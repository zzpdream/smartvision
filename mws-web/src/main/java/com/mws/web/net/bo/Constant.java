package com.mws.web.net.bo;

import io.netty.util.AttributeKey;

/**
 * Created by ranfi on 2/22/16.
 */
public class Constant {

    //协议固定的头
    public final static int magicWord = 0x116699ee;

    //请求协议的类型
    public final static String commandRequestType = "req";


    //返回协议的类型
    public final static String commandResponseType = "res";

    //session中存储的终端连接IP标志
    public final static String sessionClientIp = "KEY_SESSION_CLIENT_IP";
    
    public static AttributeKey<String> CLIENT_IP = AttributeKey.newInstance(Constant.sessionClientIp);

    /**
     * 终端类型枚举常量定义
     */
    public enum TerminalType {

        standard(1, "标准终端"), seat(2, "席位终端");

        public int status;
        public String name;

        TerminalType(int status, String name) {
            this.status = status;
            this.name = name;

        }
    }


}
