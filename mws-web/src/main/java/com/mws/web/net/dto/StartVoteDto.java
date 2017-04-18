package com.mws.web.net.dto;

import java.util.List;

/**
 * 主控机开始表决的请求实体
 * <p/>
 * Created by ranfi on 2/28/16.
 */
public class StartVoteDto {

    private String subject;
    private String horizontal;
    private String vertical;
    private List<String> titles;


    public String getSubject() {
        return subject;
    }

    public String getVertical() {
        return vertical;
    }

    public void setVertical(String vertical) {
        this.vertical = vertical;
    }

    public String getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(String horizontal) {
        this.horizontal = horizontal;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }
}
