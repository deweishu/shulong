package com.qkl.manage.aop;

/**
 * @author dengjihai
 * @create 2018-03-28
 **/

import com.qkl.admin.entity.Admin;
import com.qkl.common.web.ModuleProperties;
import com.qkl.common.web.RequestContext;
import com.qkl.manage.core.cache.AdminSingleSession;
import com.qkl.admin.annotaion.SystemLogAnnotation;
import com.qkl.admin.dto.PubOperLogDto;
import com.qkl.admin.service.PubOperLogService;
import com.qkl.admin.util.AopUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 用户操作日志AOP
 * @author  邓集海
 * @version 1.0
 */
@Component
@Aspect
//@Order(1)标记切面类的处理优先级,i值越小,优先级别越高.PS:可以注解类,也能注解到方法上
public class EcmSysemLogAop {

    private static final Logger LOGGER = LoggerFactory.getLogger(EcmSysemLogAop.class);

    @Autowired
    AdminSingleSession adminSingleSession;


    @Autowired
    PubOperLogService operateLogService;

    @Autowired
    ModuleProperties moduleProperties;

    /**
     * <p>Discription:[后置通知，扫描com.jia包及此包下的所有带有SystemLogAnnotation注解的方法]</p>
     * @param joinPoint 前置参数
     * @param systemLogAnnotation 自定义注解
     */
    @After(("execution(* com.qkl.manage..*.*(..)) && @annotation(systemLogAnnotation)"))
    public void doAfterAdvice(JoinPoint joinPoint, SystemLogAnnotation systemLogAnnotation) throws Exception {
        LOGGER.info("=========================================用户操作日志-后置通知开始执行......=========================================");
        String desc = systemLogAnnotation.desc();
        addSystemLog(desc,joinPoint);
        LOGGER.info("=========================================用户操作日志-后置通知结束执行......=========================================");
    }

    public void addSystemLog(String operationContent,JoinPoint joinPoint) throws Exception {
        // 获取此次请求的request对象
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        // 获取当前登录人的信息

        Admin admin =adminSingleSession.getUser();
        if(Objects.nonNull(admin)) {
            PubOperLogDto operateLog = new PubOperLogDto();
            operateLog.setModule(moduleProperties.module);
            operateLog.setOperatorId(admin.getId());
            operateLog.setMethod(RequestContext.getRequestInfo().getRequestHttpMethod().name());
            operateLog.setUri(request.getRequestURI());
            operateLog.setOperateContent(operationContent);
            operateLog.setOperatePhone(admin.getMobile());
            operateLog.setOperateName(admin.getUsername());
            operateLog.setParameter(AopUtil.getServiceMthodParams(joinPoint));
            operateLogService.save(operateLog);
        }
    }

}