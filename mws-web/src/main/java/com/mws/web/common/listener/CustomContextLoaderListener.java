package com.mws.web.common.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class CustomContextLoaderListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        Resource rsSys = context.getResource("classpath:application.properties");
        Properties prop = new Properties();
        try {
            InputStream in = rsSys.getInputStream();
            prop.load(in);
        } catch (IOException e) {
            throw new RuntimeException("读取文件application.properties出错！");
        }

        String profiles = prop.getProperty("spring.profiles.active");
        if (StringUtils.isBlank(profiles) || profiles.indexOf("@") != -1) {
            profiles = "development";
        }
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, profiles);
        super.contextInitialized(event);
    }

}
