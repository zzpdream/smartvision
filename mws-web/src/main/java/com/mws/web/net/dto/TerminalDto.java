package com.mws.web.net.dto;

import java.util.Date;

/**
 * 定义终端传输实体
 * <p/>
 * Created by ranfi on 3/6/16.
 */
public class TerminalDto {

    private Integer seatId;
    private Integer terminalType;
    private String clientIp;
    private Integer status;
    private String statusName;
    private Date updateTime;


    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public Integer getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(Integer terminalType) {
        this.terminalType = terminalType;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return TerminalStatus.getStatusName(status).label;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 派送状态
     */
    public enum TerminalStatus {
        unconnected(0, "未连接"), connected(1, "已连接"), disconnected(2, "已断开");
        public Integer value;
        public String label;


        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        TerminalStatus(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public static TerminalStatus getStatusName(Integer value) {
            TerminalStatus status = null;
            for (TerminalStatus map : TerminalStatus.values()) {
                if (value == map.value) {
                    status = map;
                    break;
                }
            }
            return status;
        }

    }


}
