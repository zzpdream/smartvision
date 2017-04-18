package com.mws.web.net.dto;

/**
 * Created by peakren on 7/9/16.
 */
public class PlayVideoParamDto extends BaseDto {

    private String url;

    private Boolean hardware = false;

    private Float x;

    private Float y;

    private Float w;

    private Float h;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getHardware() {
        return hardware;
    }

    public void setHardware(Boolean hardware) {
        this.hardware = hardware;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public Float getW() {
        return w;
    }

    public void setW(Float w) {
        this.w = w;
    }

    public Float getH() {
        return h;
    }

    public void setH(Float h) {
        this.h = h;
    }
}
