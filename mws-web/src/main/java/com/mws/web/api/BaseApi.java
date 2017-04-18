package com.mws.web.api;

import com.mws.web.common.exception.ApiRequestException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ranfi on 2/23/16.
 */
@ControllerAdvice
public class BaseApi {


    @ExceptionHandler(ApiRequestException.class)
    @ResponseBody
    public String processException(ApiRequestException request) {


        return "viewName"; //返回一个逻辑视图名
    }
}
