package com.mws.web.context;

import java.util.Properties;


/**
 * 系统中加载到内存的缓存信息
 */
public class ParameterCache {
    /**
     * 系统属性
     */
    public static Properties systemProp;

    /**
     * 用户与数据源属性
     */
    public static Properties datasourceProp;

    static {
        ParameterCache.datasourceProp = new Properties();
    }

    public static String getSystemProp(String key) {
        return systemProp.getProperty(key);
    }

    public static void setSystemProp(Properties systemProp) {

        ParameterCache.systemProp = systemProp;
    }


    public static String getDatasourceProp(String key) {
        if (null == key) {
            return "0";
        } else {
            return datasourceProp.getProperty(key);
        }
    }

    public static void setDatasourceProp(String userId, String dsId) {
        if (!datasourceProp.containsKey(userId)) {
            datasourceProp.setProperty(userId, dsId);
        }
    }
}
