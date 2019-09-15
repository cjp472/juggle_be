package com.juggle.juggle;

import com.juggle.juggle.framework.common.utils.ApplicationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableCaching
@EnableScheduling
@ComponentScan({"com.juggle"})
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@ImportResource("classpath:spring/applicationContext*.xml")
@PropertySource(value="classpath:config/application.properties",encoding = "UTF-8", ignoreResourceNotFound = true)
public class JuggleApiServerApplication extends SpringBootServletInitializer {

    private static Logger LOG = LoggerFactory.getLogger(JuggleApiServerApplication.class);
    public static void main(String[] args) {
        ApplicationUtils.CONTEXT = SpringApplication.run(JuggleApiServerApplication.class, args);
    }
}
