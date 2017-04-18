package com.mws.web.context;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 系统启动初始化，把一些系统属性从文本文件中初始化到web上下文中
 *
 * @author 任鹤峰
 * @version 1.0 2011-3-16
 * @since 1.6.20
 */
public class SystemResourceInit {

    /**
     * log4j 记录器
     */
    private static final Logger log = Logger.getLogger(SystemResourceInit.class);

    public void init() {

        Resource resource = new ClassPathResource("sys.properties");
//        InputStream in = this.getClass().getClassLoader().getResourceAsStream("classpath:sys.properties");
        Properties propSys = new Properties();
        try {
            propSys.load(new InputStreamReader(resource.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            log.error("读取文件system.properties出错！", e);
            throw new RuntimeException("读取文件system.properties出错！");
        }
        ParameterCache.setSystemProp(propSys);
    }
}

