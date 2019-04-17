package com.qkl.msg.service;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.google.common.collect.Lists;
import com.qkl.msg.assembler.PushLogAssembler;
import com.qkl.msg.bean.PushType;
import com.qkl.msg.config.GetuiPushConfig;
import com.qkl.msg.dto.PushLogDto;
import com.qkl.msg.dto.PushLogReq;
import com.qkl.msg.dto.SendPushDto;
import com.qkl.msg.entity.PushLog;
import com.qkl.msg.jpa.PushLogRepository;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
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
import org.springframework.util.Assert;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dengjihai
 * @create 2018-10-19
 **/
@Service
public class PushService {


    Logger logger= LoggerFactory.getLogger(PushService.class);

    @Autowired
    GetuiPushConfig pushConfig;

    ThreadLocal<IGtPush> pushThreadLocal= new ThreadLocal<>();

    @Autowired
    PushLogRepository pushLogRepository;

    /**
     * 发送推送通知
     * @param sendPushDto
     * @return
     */
    public String  sendPush(SendPushDto sendPushDto){
        if(pushThreadLocal.get()==null){
            IGtPush push = new IGtPush(pushConfig.getUrl(), pushConfig.getAppKey(), pushConfig.getMasterSecret());
            pushThreadLocal.set(push);
        }
        IGtPush push =pushThreadLocal.get();
        NotificationTemplate template =new NotificationTemplate();
        template.setAppId(pushConfig.getAppId());
        template.setAppkey(pushConfig.getAppKey());
        template.setTransmissionContent(sendPushDto.getContent());
        //收到消息是否立即启动应用： 1为立即启动，2则广播等待客户端自启动
        template.setTransmissionType(1);
        Style0 style = new Style0();
        style.setTitle(sendPushDto.getTitle());
        style.setText(sendPushDto.getText());
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(sendPushDto.getRing());
        style.setVibrate(sendPushDto.getVibrate());
        style.setClearable(true);//设置可清除
        template.setStyle(style);
        List<String> appIds = new ArrayList<>();
        appIds.add(pushConfig.getAppId());
        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);
        IPushResult ret = push.pushMessageToApp(message);
        String result=ret.getResponse().toString();
        logger.info("\n ### 推送结果：{}",ret.getResponse().toString());
        return addSendPushLog(sendPushDto,result);
    }

    @Transactional(rollbackFor = Exception.class)
    public String addSendPushLog(SendPushDto sendPushDto,String result){
        PushLog pushLog=new PushLog();
        pushLog.setOperateId(sendPushDto.getOperateId());
        pushLog.setOperateName(sendPushDto.getOperateName());
        pushLog.setPushContent(sendPushDto.getContent());
        pushLog.setPushTitle(sendPushDto.getTitle());
        pushLog.setPushTxt(sendPushDto.getText());
        pushLog.setPushType(PushType.GROUP);
        pushLog.setPushResult(result);
        return pushLogRepository.save(pushLog).getId();
    }


    public PushLogDto findOne(String id){
        Assert.notNull(id,"ID不能为空");
        PushLog pushLog=pushLogRepository.findOne(id);
        return PushLogAssembler.convertToDto(pushLog);
    }

    public Page<PushLogDto> findPage(PushLogReq operLogReq) {
        PageRequest pageRequest = new PageRequest(operLogReq.getPageNumber(), operLogReq.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
        Specification<PushLog> spec = (root, query, builder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            // search by parameters
            if (StringUtils.isNotEmpty(operLogReq.getQueryLike())) {
                Path<String> name = root.get("pushTitle");
                Predicate nickLike = builder.like(name, "%" + operLogReq.getQueryLike() + "%", '/');
                predicates.add(builder.or(nickLike));
            }

            Path<String> operateName = root.get("operateName");
            Predicate operateLike = builder.notEqual(operateName, "admin");
            predicates.add(operateLike);

            query.orderBy(builder.desc(root.get("createTime")));
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        Page<PushLog> operLogPage = pushLogRepository.findAll(spec,pageRequest);
        Page<PushLogDto> dtoPage = new PageImpl<>(PushLogAssembler.convertToDtoList(operLogPage.getContent()), pageRequest,operLogPage.getTotalElements());
        return dtoPage;
    }

}
