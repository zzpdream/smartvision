package com.mws.model;

import org.hibernate.annotations.Index;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Created by ranfi on 3/13/16.
 */
@Entity
@Table(name = "terminal")
public class Terminal extends IdEntity<Integer> {

    private String ip; //终端IP
    private Integer seatId = -1; //座位号
    private Integer register; //报到状态 0:未报到 1: 已报到 2:手动补到
    private Integer terminalType; //终端类型 1：正式终端 2：列席终端
    private Integer votingRights; //是否有表决权 0: 无表决权 1:有表决权
    private Integer versionCode; //终端android软件升级版本号，是整形，用于后期apk升级
    private String appVersion; //终端软件发布版本号
    private Integer status; //终端当前的业务状态
    private Integer latestCommandStatus; //最新指令状态
    private Integer connectStatus; //终端连接状态  0:未连接 1:已连接
    private Timestamp updateTime;

    private String connectStatusName;


    @NotNull
    @Index(name = "idx_ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getRegister() {
        return register;
    }

    public void setRegister(Integer register) {
        this.register = register;
    }

    public Integer getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(Integer terminalType) {
        this.terminalType = terminalType;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public Integer getVotingRights() {
        return votingRights;
    }

    public void setVotingRights(Integer votingRights) {
        this.votingRights = votingRights;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getConnectStatus() {
        return connectStatus;
    }

    public void setConnectStatus(Integer connectStatus) {
        this.connectStatus = connectStatus;
    }

    public Integer getLatestCommandStatus() {
        return latestCommandStatus;
    }

    public void setLatestCommandStatus(Integer latestCommandStatus) {
        this.latestCommandStatus = latestCommandStatus;
    }

    @Transient
    public String getConnectStatusName() {
        if (null == connectStatus) {
            return TerminalStatus.unconnected.label;
        }
        return TerminalStatus.getStatusName(connectStatus).label;
    }

    public void setConnectStatusName(String connectStatusName) {
        this.connectStatusName = connectStatusName;
    }

    /**
     * 终端状态
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

    /**
     * 终端类型
     */
    public enum TerminalType {
        NORMAL(1, "正式终端"), ATTEND(2, "列席终端");
        public int value;
        public String label;


        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        TerminalType(int value, String label) {
            this.value = value;
            this.label = label;
        }

        public static TerminalType getStatusName(Integer value) {
            TerminalType status = null;
            for (TerminalType map : TerminalType.values()) {
                if (value == map.value) {
                    status = map;
                    break;
                }
            }
            return status;
        }

    }
}
