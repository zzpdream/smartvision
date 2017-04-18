package com.mws.web.net.dto;

import java.util.List;

/**
 * Created by ranfi on 2/28/16.
 */
public class StopVoteDto {


    private List<VoteResultDto> results;

    public List<VoteResultDto> getResults() {
        return results;
    }

    public void setResults(List<VoteResultDto> results) {
        this.results = results;
    }
}
