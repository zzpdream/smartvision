package com.mws.web.net.dto.client;

import com.mws.web.net.dto.BaseDto;
import com.mws.web.net.dto.VoteResultDto;

import java.util.List;

/**
 * Created by ranfi on 3/30/16.
 */
public class StopVoteClientDto extends BaseDto {

    private static final long serialVersionUID = 5278653227479069617L;

    private Integer type;
    private String title;
    private List<VoteResultDto> votes;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<VoteResultDto> getVotes() {
        return votes;
    }

    public void setVotes(List<VoteResultDto> votes) {
        this.votes = votes;
    }
}
