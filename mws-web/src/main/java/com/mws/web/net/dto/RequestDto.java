package com.mws.web.net.dto;

/**
 * 请求接口的传输对象
 * Created by ranfi on 2/22/16.
 */
public class RequestDto {

    private Integer seatId;  //座位号

    private String title;  //开始报道用到的标题


    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
