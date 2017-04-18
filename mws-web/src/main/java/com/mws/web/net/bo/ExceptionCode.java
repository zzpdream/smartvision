package com.mws.web.net.bo;

/**
 * Created by ranfi on 2/23/16.
 */
public enum ExceptionCode {

    SUCCESS(0, "成功"),

    CMD_NOT_EXISTS(100, "请求命令协议不存在"),

    MAGIC_WORD_NOT_EXISTS(101, "magic word not exists"),

    TERMINAL_STATUS_NOT_EXISTS(102, "终端业务状态不存在"),

    SEAT_NOT_FOUND(200, "座位不存在"),

    SEAT_INCONSISTENT(201, "座位不一致"),

    VOTE_INCONSISTENT(300, "表决权不一致");


    public int code;
    public String name;

    ExceptionCode(int code, String name) {
        this.code = code;
        this.name = name;
    }


}
