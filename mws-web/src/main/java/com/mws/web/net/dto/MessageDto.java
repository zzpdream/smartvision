package com.mws.web.net.dto;


import java.io.Serializable;
import java.util.Map;

import com.mws.web.net.bo.Constant;

/**
 * 消息结构体
 */
public class MessageDto {

    /**
     * 消息请求结构体对象
     */
    public static class Request implements Serializable {

        private static final long serialVersionUID = -2970653856892719229L;

        private String type;
        private String cmd; //请求命令字
        private Object params; //参数对象 可以是entity和Map


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCmd() {
            return cmd;
        }

        public void setCmd(String cmd) {
            this.cmd = cmd;
        }

        public Object getParams() {
            return params;
        }

        public void setParams(Object params) {
            this.params = params;
        }
    }

    public static class Response implements Serializable {

        private static final long serialVersionUID = 1036491213523151441L;

        private String type = Constant.commandResponseType;
        private String cmd;
        private Integer status = 0;
        private Map<String, Object> values;


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCmd() {
            return cmd;
        }

        public void setCmd(String cmd) {
            this.cmd = cmd;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Map<String, Object> getValues() {
            return values;
        }

        public void setValues(Map<String, Object> values) {
            this.values = values;
        }
    }


}


