package com.mws.web.net.dto.client;

import com.mws.web.net.dto.BaseDto;

/**
 * Created by ranfi on 3/30/16.
 */
public class ChairmanClientDto extends BaseDto {


    private static final long serialVersionUID = -2127447500052298540L;


    private String videoUrl;


    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
