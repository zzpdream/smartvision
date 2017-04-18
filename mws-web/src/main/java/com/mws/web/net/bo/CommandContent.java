package com.mws.web.net.bo;

/**
 * 用于存储主控机下发给代表主机的数据对象
 * <p/>
 * Created by ranfi on 2/29/16.
 */
public class CommandContent {

    private Command cmd;
    private Object content;


    public Command getCmd() {
        return cmd;
    }

    public void setCmd(Command cmd) {
        this.cmd = cmd;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
