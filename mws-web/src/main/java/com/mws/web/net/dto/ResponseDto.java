package com.mws.web.net.dto;

/**
 * Created by ranfi on 2/22/16.
 */
public class ResponseDto {

    private Integer code;
    private String msg;

    private Object results;


    public ResponseDto() {
        this.code = 0;
        this.msg = "OK";
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public Object getResults() {
        return results;
    }

    public void setResults(Object results) {
        this.results = results;
    }
}
