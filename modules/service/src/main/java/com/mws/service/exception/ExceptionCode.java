package com.mws.service.exception;

/**
 * 异常码
 */
public enum ExceptionCode {

    NORMAL(0, "OK"),

    // 系统级
    DEFAULT_ERROR_MSG(10000, "服务器被外星人劫走了，请稍后再试!"),
    SERVEREXCEPTION(10001, "服务器异常!"), // 服务器异常
    METHODDOESNOTEXIST(10002, "Method does not exist!"), // 接口不存在
    CLIENTAPIERROR(10003, "client api error!"), // 接口不存在
    CLIENT_EXCEPTION(10004, "客户端异常"),

    MISSING_ACCESS_TOKEN(20001, "missing access_token"), // 缺少access_token
    INVALID_ACCESS_TOKEN(20002, "invalid access_token"), // 无效的accessToken
    INVALID_REFRESH_TOKEN(20003, "invalid refresh_token"), // 无效的refresh_token
    PARAMEXCEPTION(20101, "Param to bean exception!"), // Param参数转换异常
    USERNOTEMPTY(20102, "User cannot be empty!"),
    ONEPARAMISEMPTY(20103, "One param is empty!"),
    MISSINGPARAMETER(20104, "Missing parameter!"),
    UPLOADFILEEXCEPTION(20105, "upload file exception!");


    public final Integer errorCode;
    public final String errorMsg;

    ExceptionCode(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public static ExceptionCode getExceptionCode(final String exceptionCode) {
        try {
            System.out.println("----exceptionCode----" + exceptionCode);
            return ExceptionCode.valueOf(exceptionCode);
        } catch (Exception e) {
        }

        return SERVEREXCEPTION;
    }


}
