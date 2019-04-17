package com.qkl.msg.service;

import com.google.common.collect.Lists;
import com.qkl.common.config.VerifyCodeConfig;
import com.qkl.common.constant.CodeConstant;
import com.qkl.common.util.RandomUtil;
import com.qkl.common.util.ServiceAssert;
import com.qkl.common.util.StringUtil;
import com.qkl.common.util.TimeUtil;
import com.qkl.common.web.AppException;
import com.qkl.common.web.StandardResponseHeader;
import com.qkl.msg.assembler.SmsRecordAssembler;
import com.qkl.msg.bean.SendStatus;
import com.qkl.msg.bean.SmsTemplate;
import com.qkl.msg.dto.SmsRecordDto;
import com.qkl.msg.dto.SmsRecordReq;
import com.qkl.msg.entity.SmsRecord;
import com.qkl.msg.jpa.SmsRecordRepository;
import com.qkl.sms.cache.SmsCodeCache;
import com.qkl.sms.dto.SendSmsResult;
import com.qkl.sms.service.SmsSendCallback;
import com.qkl.sms.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 短信发送记录接口
 * Created by 6299 on 2018/5/31.
 * Modify by dengjihai on 2018/8/10.
 */
@Service
public class SmsRecordService {

    private static final Logger logger = LoggerFactory.getLogger(SmsRecordService.class);

    @Autowired
    private SmsRecordRepository smsRecordRepository;
    @Autowired
    private SmsService smsService;
    @Autowired
    private VerifyCodeConfig verifyCodeConfig;
    @Autowired
    private SmsCodeCache smsCodeCache;


    @Transactional(rollbackFor = Exception.class)
    public SmsRecord save(SmsRecordDto smsRecordDto) {
        SmsRecord smsRecord = SmsRecordAssembler.convertToEntity(smsRecordDto, null);
        SmsRecord save = smsRecordRepository.save(smsRecord);
        return save;
    }

    /**
     * 发送短信验证码
     * @param mobile 接收短信手机号
     * @param templateCode 短信模板，短信内容即模板配置的内容
     * @return
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public SendSmsResult sendSmsCode(String mobile, SmsTemplate templateCode){
        SendSmsResult sendSmsResult = new SendSmsResult();

        Date startTime=TimeUtil.getTodayStartTime();
        Date endTime=TimeUtil.getTodayEndTime();
        List<SmsRecord> smsRecords=smsRecordRepository.findByMobileAndCreateTimeBetween(mobile,startTime,endTime);
        //如果当天同一个手机号，大于等于5次，就不允许发送
        if(smsRecords!=null && smsRecords.size()>=5){
            throw new AppException(StandardResponseHeader.SEND_SMS_MAX_NUM);
        }


        List<SmsRecord> smsRecordList=smsRecordRepository.findByMobileOrderByCreateTimeDesc(mobile);

        //每150s之内只能获取一次验证码
        if(smsRecordList!=null && smsRecordList.size()>0){
            SmsRecord smsRecord=smsRecordList.get(0);//取出最近一条的发送时间
            Date time=smsRecord.getCreateTime();
            long interval = (TimeUtil.now().getTime() - time.getTime())/1000;
//            Long second=TimeUtil.getDistanceMintues(time,TimeUtil.now());
            if(interval<60){
                throw new AppException(StandardResponseHeader.SEND_SMS_OUT_NUM);
            }

        }
        try {
            // 开发环境和测试环境，无需发送验证码
            if (verifyCodeConfig.isClose()) {
                String sendCode = CodeConstant.DEFAULT_CODE;
                smsCodeCache.saveSmsCode(mobile, sendCode);
                sendSmsResult.setResult(true);
                sendSmsResult.setMsg("短信验证码发送成功");
                logger.info("\n###短信发送开关：已关闭（即不发送真实短信），本次短信验证码为：{}", sendCode);
            } else {
                String sendCode = RandomUtil.getSimpleRandomCodeNotRepeat(4);
                //保存发送记录
                String content = replaceContent(sendCode,templateCode.getName());
                SmsRecordDto smsRecordDto = new SmsRecordDto(templateCode.getCode(), mobile, sendCode, content);
                smsRecordDto.setStatus(SendStatus.SENDED.getCode());
                SmsRecord smsRecord = save(smsRecordDto);

                SmsSendCallback smsSendCallback = (result, msg) -> {
                    logger.info("\n发送短信验证码的回调方法，手机号：{}", mobile);
                    sendSmsResult.setMsg(msg);
                    sendSmsResult.setResult(result);
                    if (result) {
                        // 发送成功缓存验证码
                        smsCodeCache.saveSmsCode(mobile, sendCode);
                        smsRecord.setStatus(SendStatus.SENDED_SUCCESS);
                        logger.info("\n发送短信验证码成功，手机号：{}，缓存验证码：{}", mobile, sendCode);
                    } else {
                        smsRecord.setStatus(SendStatus.SENDED_ERROR);
                        logger.info("\n发送短信验证码失败！手机号：{}，失败原因：{}", mobile, msg);
                    }
                    //修改发送记录的状态
                    smsRecordRepository.save(smsRecord);
                };

                // 语音短信
                if (SmsTemplate.CL_VOICE_SMS.equals(templateCode)) {
                    logger.info("\n发送语音短信，手机：{}", mobile);
                    smsService.sendVoiceSms(content, mobile, smsSendCallback);
                } else { // 普通短信
                    logger.info("\n 发送短信验证码，手机：{}", mobile);
                    smsService.sendSms(content, mobile, smsSendCallback);
                }
            }
        } catch (Exception e) {
            logger.error("发送短信失败：", e);
            throw new AppException(StandardResponseHeader.SEND_SMS_FAIL);
        }
        return sendSmsResult;
    }

    private String replaceContent(String code,String content){
        return content.replace("{code}", code);
    }


    /**
     * 发送短信消息
     * @param content 短信内容
     * @param mobile 接收短信手机号
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public SendSmsResult sendSms(String content, String mobile){
        ServiceAssert.isTrue(StringUtil.isNotBlank(mobile), StandardResponseHeader.ERROR_PARAM.getCode(), "手机号码不能为空");
        ServiceAssert.isTrue(StringUtil.isNotBlank(content), StandardResponseHeader.ERROR_PARAM.getCode(), "短信内容不能为空");

        SendSmsResult sendSmsResult = new SendSmsResult();
        try {
            // 检查短信发送开关是否已关闭
            if (verifyCodeConfig.isClose()) {
                sendSmsResult.setResult(true);
                sendSmsResult.setMsg("短信消息发送成功");
                logger.info("\n###短信发送开关：已关闭（即不发送真实短信），本次短信内容为：{}", content);
            } else {
                // 保存发送记录
                SmsRecordDto smsRecordDto = new SmsRecordDto(SmsTemplate.CL_SMS.getCode(), mobile, "无", content);
                smsRecordDto.setStatus(SendStatus.SENDED.getCode());
                SmsRecord smsRecord = save(smsRecordDto);

                SmsSendCallback smsSendCallback = (result, msg) -> {
                    sendSmsResult.setMsg(msg);
                    sendSmsResult.setResult(result);
                    if (result) {
                        smsRecord.setStatus(SendStatus.SENDED_SUCCESS);
                        logger.info("\n发送短信消息成功，手机号：{}", mobile);
                    } else {
                        smsRecord.setStatus(SendStatus.SENDED_ERROR);
                        logger.info("\n发送短信消息失败！手机号：{}，失败原因：{}", mobile, msg);
                    }
                    //修改发送记录的状态
                    smsRecordRepository.save(smsRecord);
                };

                // 普通短信
                smsService.sendSms(content, mobile, smsSendCallback);
            }
        } catch (Exception e) {
            logger.error("发送短信失败：", e);
            throw new AppException(StandardResponseHeader.SEND_SMS_FAIL);
        }
        return sendSmsResult;
    }


    /**
     * 查询短信记录
     * @param smsRecordReq
     * @return
     */
    public Page<SmsRecordDto> findPage(SmsRecordReq smsRecordReq) {
        PageRequest pageRequest = new PageRequest(smsRecordReq.getPageNumber(), smsRecordReq.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
        Specification<SmsRecord> spec = (root, query, builder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            if (StringUtil.isNotBlank(smsRecordReq.getMobile())) {
                predicates.add(builder.equal(root.get("mobile"), smsRecordReq.getMobile()));
            }
            if (Objects.nonNull(smsRecordReq.getTemplateCode())) {
                SmsTemplate smsTemplate = SmsTemplate.getProperty(SmsTemplate.class, smsRecordReq.getTemplateCode());
                if (Objects.nonNull(smsTemplate)) {
                    predicates.add(builder.equal(root.get("templateCode"), smsTemplate));
                }
            }
            query.orderBy(builder.desc(root.get("createTime")));
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        Page<SmsRecord> noticePage= smsRecordRepository.findAll(spec,pageRequest);
        return new PageImpl<>(SmsRecordAssembler.convertToDtoList(noticePage.getContent()), pageRequest,noticePage.getTotalElements());
    }
}
