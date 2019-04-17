package com.qkl.api.user.web;

import com.qkl.api.base.web.BaseController;
import com.qkl.api.user.cache.TokenSessionCache;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.user.dto.FeedbackDto;
import com.qkl.user.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author dengjihai
 * @create 2018-09-06
 **/
@RestController
@RequestMapping("/feed/back")
public class FeedBackController extends BaseController {


    @Autowired
    FeedbackService feedbackService;


    @Autowired
    TokenSessionCache tokenSessionCache;

    @PostMapping("/commit")
    public JsonResponse addFeedBack(@Valid @RequestBody FeedbackDto feedbackDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return WebUtil.errorJsonResponse(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        feedbackDto.setUserId(tokenSessionCache.getUserId());
        feedbackService.save(feedbackDto);
        return WebUtil.successJsonResponse("谢谢您的反馈，我们会进一步改善");
    }

}
