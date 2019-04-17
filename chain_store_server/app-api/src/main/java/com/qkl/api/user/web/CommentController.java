package com.qkl.api.user.web;

import com.qkl.api.base.web.BaseController;
import com.qkl.api.user.cache.TokenSessionCache;
import com.qkl.api.user.dto.VerifyCodeRequest;
import com.qkl.apk.service.ApkService;
import com.qkl.common.util.BigDecimalUtil;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.HalfOpen;
import com.qkl.common.web.JsonResponse;
import com.qkl.sms.cache.SmsCodeCache;
import com.qkl.user.dto.CommentListResp;
import com.qkl.user.dto.CommentLogDto;
import com.qkl.user.dto.CommentLogReq;
import com.qkl.user.service.CommentLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * 评论提交
 */
@Api(description = "评论相关接口")
@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController {


    @Autowired
    CommentLogService commentLogService;

    @Autowired
    TokenSessionCache tokenSessionCache;


    @Autowired
    ApkService apkService;

    @ApiOperation(value="对应用评论进行踩接口", notes="对应用评论进行踩接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/love/num/sub/{commentId}")
    public JsonResponse subCommentLoveNum(@PathVariable String commentId) {

        String userId=tokenSessionCache.getUserId();
        Boolean add=commentLogService.subCommentNoNum(commentId,userId);
        if (add) {
            return WebUtil.successJsonResponse("踩成功");
        }else{
            return WebUtil.errorJsonResponse("您已经踩啦~");
        }
    }


    @ApiOperation(value="对应用评论进行点赞接口", notes="对应用评论进行点赞接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/love/num/add/{commentId}")
    public JsonResponse addCommentLoveNum(@PathVariable String commentId) {

        String userId=tokenSessionCache.getUserId();
        Boolean add=commentLogService.addCommentLoveNum(commentId,userId);
        if (add) {
            return WebUtil.successJsonResponse("点赞成功");
        }else{
            return WebUtil.errorJsonResponse("您已经点赞过啦~");
        }
    }

    @ApiOperation(value="对应用进行评论接口", notes="对应用进行评论接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/commit/log")
    public JsonResponse addComment(@Valid @RequestBody CommentLogDto commentLogDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return WebUtil.errorJsonResponse(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        String userId=tokenSessionCache.getUserId();
        commentLogDto.setUserId(userId);
        commentLogService.save(commentLogDto);

        return WebUtil.successJsonResponse("发表评论成功");
    }


    @ApiOperation(value="通过应用ID，查询出评论信息", notes="通过应用ID，查询出评论信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/log/list")
    public JsonResponse commentList(@Valid @RequestBody CommentLogReq commentLogReq,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return WebUtil.errorJsonResponse(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        commentLogReq.setPageNumber((commentLogReq.getPageNumber()-1)*commentLogReq.getPageSize());
        CommentListResp commentListResp =new CommentListResp();
        commentListResp.setCommentData(commentLogService.findPage(commentLogReq));
        commentListResp.setGoodScoreNum(commentLogService.getCommentScore(commentLogReq.getApkId(),5)+commentLogService.getCommentScore(commentLogReq.getApkId(),4));
        commentListResp.setBadScoreNum(commentLogService.getCommentScore(commentLogReq.getApkId(),1));
        commentListResp.setSoScoreNum(commentLogService.getCommentScore(commentLogReq.getApkId()));
        Integer totalScoreNum=commentLogService.sumApkNum(commentLogReq.getApkId());
        if(totalScoreNum!=null){
            Integer totalPoepleNum=Long.valueOf(commentListResp.getCommentData().getTotalElements()).intValue();
            BigDecimal totalSN=new BigDecimal(totalScoreNum+"");
            BigDecimal totalPN=new BigDecimal(totalPoepleNum+"");
            BigDecimal scroe=totalSN.divide(totalPN, 1, BigDecimal.ROUND_HALF_DOWN);
            commentListResp.setTotalScore(scroe+"");
            //更新apk的分数
            apkService.updateApkScoreNum(commentLogReq.getApkId(),scroe.toPlainString());
        }else{
            commentListResp.setTotalScore("5");
        }
        return WebUtil.successJsonResponse("获取应用的评论信息成功",commentListResp);
    }

}
