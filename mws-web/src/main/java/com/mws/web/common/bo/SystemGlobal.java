package com.mws.web.common.bo;

import com.mws.web.context.ParameterCache;

/**
 * 全局的常量
 */
public class SystemGlobal {

    public final static int PAGE_SIZE = 20;


    /**
     * 获取资源的下载地址
     *
     * @return
     */
    public static String getResourceDownloadUrl() {
        return ParameterCache.getSystemProp("resource.download.url");
    }


    /**
     * 获取资源上传文件夹路径
     *
     * @return
     */
    public static String getResourceUploadFolder() {
        return ParameterCache.getSystemProp("resource.upload.folder");
    }


}
