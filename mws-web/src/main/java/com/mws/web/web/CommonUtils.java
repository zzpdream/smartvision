package com.mws.web.web;

import javax.servlet.http.HttpServletRequest;

/**
 * Utils - 常用
 *
 * @author xingkong1221
 * @date 2015-10-12
 */
public class CommonUtils {

    /**
     * 判断请求是否为异步请求
     *
     * @param request 请求
     * @return 是否为异步请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        String header = request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header)) {
            return true;
        } else if (!"".equals(accept) && null != accept) {
            return accept.indexOf("application/json") >= 0;
        }
        return false;
    }

}
