package com.mws.web.net.dto.client;

import com.mws.web.net.dto.BaseDto;

import java.util.List;

/**
 * Created by ranfi on 3/30/16.
 */
public class ShowLogoClientDto extends BaseDto {


    private static final long serialVersionUID = 7333037754646524877L;

    private List<String> titles;

    private String picUrl;


    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
