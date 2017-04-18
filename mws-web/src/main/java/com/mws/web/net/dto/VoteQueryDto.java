package com.mws.web.net.dto;

import java.util.List;

/**
 * 主控机查询代表主机表决实体
 * <p/>
 * Created by ranfi on 3/7/16.
 */
public class VoteQueryDto {

    private Integer seatId;

    private List<VoteQueryItemDto> results;


    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public List<VoteQueryItemDto> getResults() {
        return results;
    }

    public void setResults(List<VoteQueryItemDto> results) {
        this.results = results;
    }
}
