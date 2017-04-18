/**
 * Copyright (c) 2005-2012 springside.org.cn
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.mws.core.jetty;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建Jetty Server的工厂类.
 *
 * @author ranfi
 */
public class JettyFactory {

    private static final String DEFAULT_WEBAPP_PATH = "src/main/webapp";
    private static final String WEBDEFAULT_PATH = "src/main/resources/webdefault.xml";

    /**
     * 创建用于开发运行调试的Jetty Server, 以src/main/webapp为Web应用目录.
     */
    public static Server createServerInSource(int port, String contextPath) {
        Server server = new Server();
        // 设置在JVM退出时关闭Jetty的钩子。
        server.setStopAtShutdown(true);

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(port);
        // 解决Windows下重复启动Jetty居然不报告端口冲突的问题.
        connector.setReuseAddress(false);
        server.setConnectors(new Connector[]{connector});

        WebAppContext webContext = new WebAppContext();
        webContext.setResourceBase(DEFAULT_WEBAPP_PATH);
        webContext.setContextPath(contextPath);
        // 修改webdefault.xml，解决Windows下Jetty Lock住静态文件的问题.
        webContext.setDefaultsDescriptor(WEBDEFAULT_PATH);
        webContext.setParentLoaderPriority(true);
        server.setHandler(webContext);

        return server;
    }

    /**
     * 创建用于开发运行调试的Jetty Server, 以src/main/webapp为Web应用目录.
     */
    public static Server createServerInSource(int port, String contextPath, String baseProjectPath) {
        Server server = new Server();
        // 设置在JVM退出时关闭Jetty的钩子。
        server.setStopAtShutdown(true);

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(port);
        // 解决Windows下重复启动Jetty居然不报告端口冲突的问题.
        connector.setReuseAddress(false);
        server.setConnectors(new Connector[]{connector});

        WebAppContext webContext = new WebAppContext();
        webContext.setResourceBase(baseProjectPath + DEFAULT_WEBAPP_PATH);
        webContext.setContextPath(contextPath);
        // 修改webdefault.xml，解决Windows下Jetty Lock住静态文件的问题.
        webContext.setDefaultsDescriptor(baseProjectPath + WEBDEFAULT_PATH);
        webContext.setParentLoaderPriority(true);
        server.setHandler(webContext);

        return server;
    }

    /**
     * 设置除jstl-*.jar外其他含tld文件的jar包的名称. jar名称不需要版本号，如sitemesh, shiro-web
     */
    public static void setTldJarNames(Server server, String... jarNames) {
        WebAppContext context = (WebAppContext) server.getHandler();
        List<String> jarNameExprssions = new ArrayList<String>();
        jarNameExprssions.add(".*/jstl-[^/]*\\.jar$");
        jarNameExprssions.add(".*/.*taglibs[^/]*\\.jar$");
        for (String jarName : jarNames) {
            jarNameExprssions.add(".*/" + jarName + "-[^/]*\\.jar$");
        }

        context.setAttribute(
                "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                StringUtils.join(jarNameExprssions, '|'));

    }

    /**
     * 快速重新启动application，重载target/classes与target/test-classes.
     */
    public static void reloadContext(Server server) throws Exception {
        WebAppContext context = (WebAppContext) server.getHandler();

        System.out.println("[INFO] Application reloading");
        context.stop();

        WebAppClassLoader classLoader = new WebAppClassLoader(context);
        classLoader.addClassPath("build/classes");
        classLoader.addClassPath("build/resources");
        context.setClassLoader(classLoader);

        context.start();

        System.out.println("[INFO] Application reloaded");
    }
}