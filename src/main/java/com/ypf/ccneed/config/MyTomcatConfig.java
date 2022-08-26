package com.ypf.ccneed.config;

import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: ccneed
 * @author: yanpengfan
 * @create: 2022-07-30 16:21
 **/
@Configuration
public class MyTomcatConfig {
    /**
     * 解决异常信息：
     * java.lang.IllegalArgumentException:
     * Invalid character found in the request target. The valid characters are defined in RFC 7230 and RFC 3986
     *
     * @return TomcatEmbeddedServletContainerFactory 2.0版本以下是这个类
     * TomcatServletWebServerFactory 2.0版本以上是这个类
     */
    @Bean(name = "webServerFactory")
    public ServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
        tomcatServletWebServerFactory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> connector.setProperty("relaxedQueryChars", "[]{}"));
        return tomcatServletWebServerFactory;
    }
}

