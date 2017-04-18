package com.mws.web.net.dto;

import java.util.List;

/**
 * Created by ranfi on 2/26/16.
 */
public class MemberDto {

    private Integer registered = 0;  //是否报名,用于会议中的换座

    private String title;

    private List<SeatDto> members;


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

    public List<SeatDto> getMembers() {
        return members;
    }

    public void setMembers(List<SeatDto> members) {
        this.members = members;
    }
}
