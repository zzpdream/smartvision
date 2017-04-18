package com.mws.web.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter - shiro登陆认证
 *
 * @author xingkong1221
 * @date 2015-10-08
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        if (!isAjaxRequest((HttpServletRequest)request)) {
        	return super.onLoginSuccess(token, subject, request, response);
        }
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("{\"code\": 0, \"msg\": \"登陆成功\", \"redirectUrl\": \""+
                ((HttpServletRequest) request).getContextPath() + getSuccessUrl() +"\"}");
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
                                     ServletResponse response) {
        if (!isAjaxRequest((HttpServletRequest) request)) {
            return super.onLoginFailure(token, e, request, response);
        }
        response.setContentType("application/json;charset=utf-8");
        String msg = "登陆失败";
        if (e instanceof IncorrectCredentialsException) {
            msg = "账号密码错误";
        } else if (e instanceof DisabledAccountException) {
            msg = "账户被禁用";
        } else if (e instanceof UnknownAccountException) {
            msg = "未知账户";
        }
        try {
            response.getWriter().write("{\"code\": 10, \"msg\": \""+ msg +"\"}");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    /**
     * 判断请求是否为异步请求
     *
     * @param request 请求
     * @return 异步请求返回true, 反之返回false
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        if (StringUtils.isNoneBlank(requestType) && "XMLHttpRequest".equals(requestType)) {
            return true;
        }
        return false;
    }
}
