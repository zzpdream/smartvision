package com.mws.web.net.dto;

import java.io.Serializable;

/**
 * Created by ranfi on 2/23/16.
 */
public class SeatDto implements Serializable {

    private Integer seatId; //座位号
    private String memberId; //人员ID
    private String memberName; //人员名称
    private String duty; //职位名称
    private Integer votingRights; //是否表决权
    private String card1;
    private String card2;
    private Integer registered;
    private String title;
    private Integer askForLeave; //是否请假 1:是请假 0: 正常情况
    private Integer cardNoCheck;
    private Integer isAnonAccess;  //是否匿名访问,匿名访问没有权限查看会议内容  1: 是  0: 否


    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public Integer getVotingRights() {
        return votingRights;
    }

    public void setVotingRights(Integer votingRights) {
        this.votingRights = votingRights;
    }

    public String getCard1() {
        return card1;
    }

    public void setCard1(String card1) {
        this.card1 = card1;
    }

    public String getCard2() {
        return card2;
    }

    public void setCard2(String card2) {
        this.card2 = card2;
    }

    public Integer getRegistered() {
        return registered;
    }

    public void setRegistered(Integer registered) {
        this.registered = registered;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAskForLeave() {
        return askForLeave;
    }

    public void setAskForLeave(Integer askForLeave) {
        this.askForLeave = askForLeave;
    }

    public Integer getCardNoCheck() {
        return cardNoCheck;
    }

    public void setCardNoCheck(Integer cardNoCheck) {
        this.cardNoCheck = cardNoCheck;
    }

    public Integer getIsAnonAccess() {
        return isAnonAccess;
    }

    public void setIsAnonAccess(Integer isAnonAccess) {
        this.isAnonAccess = isAnonAccess;
    }
}
