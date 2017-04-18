package com.mws.web.net.dto;

import java.util.List;

/**
 * 终端上报表决结果的实体
 * <p/>
 * Created by ranfi on 3/7/16.
 */
public class VoteDto {

    private Integer seatId; //终端座位号

    private Integer type;   // 0 单项表决   1 多项表决

    private List<VoteItemDto> votes;  //表决结果


    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<VoteItemDto> getVotes() {
        return votes;
    }

    public void setVotes(List<VoteItemDto> votes) {
        this.votes = votes;
    }
}
