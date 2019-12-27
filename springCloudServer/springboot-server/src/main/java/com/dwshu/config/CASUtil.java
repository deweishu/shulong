package com.dwshu.config;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dwshu
 * @create 2019/12/22
 */

@Configuration
public class CASUtil {

    public static String getAccountNameFromCas(HttpServletRequest request){
        Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
        if(assertion!=null) {
            AttributePrincipal principal = assertion.getPrincipal();
            return principal.getName();
        }
        return null;
    }
}
