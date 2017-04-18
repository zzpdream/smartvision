package com.mws.web.net.dto;

/**
 * Created by ranfi on 4/19/16.
 */
public class InitializeDto extends BaseDto {

    private static final long serialVersionUID = 2230040267758849472L;

    private Integer brightness;

    public Integer getBrightness() {
        return brightness;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
    }
}
