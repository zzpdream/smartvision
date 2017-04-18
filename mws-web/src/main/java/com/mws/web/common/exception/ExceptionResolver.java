package com.mws.web.common.exception;

import com.mws.web.web.JsonMap;
import com.google.gson.Gson;
import com.mws.web.web.CommonUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理
 *
 * @author xingkong1221
 * @date 2015-10-12
 */
public class ExceptionResolver extends SimpleMappingExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String msg = "未知错误";

        if (ex instanceof WebRequestException) {
            msg = ex.getMessage();
        } else if (ex instanceof AuthorizationException) {
            msg = "权限不足";
        }

        logger.error(msg, ex);

        if (CommonUtils.isAjaxRequest(request)) {
            ModelAndView modelAndView = getModelAndView("error/async_error", ex);

            JsonMap json = new JsonMap(-100, msg);
            Gson gson = new Gson();
            modelAndView.addObject("error", gson.toJson(json));
            return modelAndView;
        }

        return super.doResolveException(request, response, handler, ex);
    }
}
