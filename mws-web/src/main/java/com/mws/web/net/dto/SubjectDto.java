package com.mws.web.net.dto;

/**
 * Created by ranfi on 2/28/16.
 */
public class SubjectDto extends BaseDto {


    private String content;
    private String horizontal;  //控制文字的水平方向, left,center,right
    private String vertical;   //控制文字的垂直方向,top,center,bottom


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(String horizontal) {
        this.horizontal = horizontal;
    }

    public String getVertical() {
        return vertical;
    }

    public void setVertical(String vertical) {
        this.vertical = vertical;
    }
}
