package com.mws.web.net.dto;

import java.io.Serializable;

/**
 * Created by ranfi on 3/30/16.
 */
public class BaseDto implements Serializable {

    private Integer seatId;


    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }
}
