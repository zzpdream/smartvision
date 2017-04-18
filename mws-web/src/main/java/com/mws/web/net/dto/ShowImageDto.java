package com.mws.web.net.dto;

/**
 * Created by ranfi on 4/19/16.
 */
public class ShowImageDto extends BaseDto {

    private static final long serialVersionUID = 2230040267758849472L;

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
