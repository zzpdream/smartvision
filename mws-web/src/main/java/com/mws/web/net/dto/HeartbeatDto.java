package com.mws.web.net.dto;

import com.mws.model.Terminal;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 每次终端和主机保持心跳的传输对象
 * <p/>
 * Created by ranfi on 2/23/16.
 */
public class HeartbeatDto implements Serializable {

    private String ip; //终端IP
    private Integer seatId = -1; //座位号
    private Integer memberId; //排座的人员ID
    private Integer register; //报到状态 0:未报到 1: 已报到 2:手动补到
    private Integer terminalType; //终端类型 1：正式终端 2：列席终端
    private Integer versionCode; //终端android软件升级版本号，是整形，用于后期apk升级
    private String appVersion; //终端软件发布版本号
    private String picZipMd5;   //照片压缩包的MD5值
    private String downloadUrl; //服务器有更新版本，则返回下载地址
    private String picZipUrl;  //服务器照片有更新，则返回下载地址
    private Integer votingRights; //表决权  0:无表决权  1:有表决权
    private Integer status; //终端当前的业务状态

    private Integer latestCommandStatus; //主控下发的最新指令状态,主要用于和终端指令同步
    private Integer retryCount = 0;   //指令恢复重试次数,最大尝试5次,否则丢弃
    private Integer connectStatus; //终端连接状态  0:未连接 1:已连接
    private Timestamp updateTime;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = null == seatId ? -1 : seatId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getRegister() {
        return register;
    }

    public void setRegister(Integer register) {
        this.register = register;
    }

    public Integer getTerminalType() {
        return null == terminalType ? Terminal.TerminalType.NORMAL.value : terminalType;
    }

    public void setTerminalType(Integer terminalType) {
        this.terminalType = terminalType;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLatestCommandStatus() {
        return latestCommandStatus;
    }

    public void setLatestCommandStatus(Integer latestCommandStatus) {
        this.latestCommandStatus = latestCommandStatus;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Integer getConnectStatus() {
        return connectStatus;
    }

    public void setConnectStatus(Integer connectStatus) {
        this.connectStatus = connectStatus;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getPicZipMd5() {
        return picZipMd5;
    }

    public void setPicZipMd5(String picZipMd5) {
        this.picZipMd5 = picZipMd5;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getPicZipUrl() {
        return picZipUrl;
    }

    public void setPicZipUrl(String picZipUrl) {
        this.picZipUrl = picZipUrl;
    }

    public Integer getVotingRights() {
        return votingRights;
    }

    public void setVotingRights(Integer votingRights) {
        this.votingRights = votingRights;
    }
}