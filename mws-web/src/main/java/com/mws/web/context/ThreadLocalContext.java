package com.mws.web.context;


/**
 * 线程局部变量
 *
 * @author 任鹤峰
 * @version 1.0 2010-3-27
 * @since JDK 1.5.0_12
 */
public class ThreadLocalContext {
    /**
     * 线程局部变量，用来保存当前用户id
     */
    public final static ThreadLocal userThreadLocal = new ThreadLocal();
    /**
     * 线程局部变量，用来保存当前操作的数据源
     */
    public final static ThreadLocal<Object> datasourceThreadLocal = new ThreadLocal<Object>();

    public static String getUserId() {
        return (String) userThreadLocal.get();
    }

    public static void setUserId(String userId) {
        userThreadLocal.set(userId);
    }

    public static Object getDatasourceId() {
        return datasourceThreadLocal.get();
    }

    public static void setDatasourceId(Object datasourceId) {
        datasourceThreadLocal.set(datasourceId);
    }
}
