package com.qkl.common.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.stereotype.Component;

/**
 * SSL 配置
 * Created by dengjihai on 2018/3/28.
 */
@Component
public class SSLConfig {

    @Value("${spring.ssl.close-http:true}")
    private boolean isCloseHttp; //是否关闭http端口，关闭后会跳转https

    public boolean isCloseHttp() {
        return isCloseHttp;
    }

    public void setCloseHttp(boolean closeHttp) {
        isCloseHttp = closeHttp;
    }

    public String getProtocol() {
        return isCloseHttp?"https":"http";
    }

//    @Bean
//    @Profile({"test", "pre", "prod"})
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
    }
//
//    @Bean
//    @Profile({"test", "pre", "prod"})
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        //Connector监听的http的端口号
        connector.setPort(80);
        connector.setSecure(!isCloseHttp());
        //监听到http的端口号后,需要判断配置是否转向到的https的端口号
        if (isCloseHttp()) {
            connector.setRedirectPort(443);
        }
        return connector;
    }
}
