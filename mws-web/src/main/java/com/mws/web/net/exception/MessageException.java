package com.mws.web.net.exception;


import com.mws.web.net.bo.ExceptionCode;

/**
 * Created by ranfi on 2/22/16.
 */
public class MessageException extends RuntimeException {


    private String exceptionDetails;
    private ExceptionCode exceptionCode;

    public MessageException() {
    }

    /**
     * @param message
     */
    public MessageException(int message) {
        super(String.valueOf(message));
    }

    /**
     * @param message
     */
    public MessageException(String message) {
        super(message);
    }

    /**
     */
    public MessageException(ExceptionCode exceptionCode) {
        this.setExceptionCode(exceptionCode);
    }

    public MessageException(ExceptionCode exceptionCode, String details) {
        this.setExceptionCode(exceptionCode);
        this.exceptionDetails = details;
    }

    /**
     * @param cause
     */
    public MessageException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getExceptionDetails() {
        return exceptionDetails;
    }

    public void setExceptionDetails(String exceptionDetails) {
        this.exceptionDetails = exceptionDetails;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
