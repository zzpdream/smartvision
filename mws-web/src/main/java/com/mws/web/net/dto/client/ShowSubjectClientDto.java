package com.mws.web.net.dto.client;

import com.mws.web.net.dto.BaseDto;

/**
 * 下发议程的实体
 * <p>
 * 继承BaseDto同时用户座位编号的属性
 * <p>
 * Created by ranfi on 3/30/16.
 */
public class ShowSubjectClientDto extends BaseDto implements Cloneable {

    private static final long serialVersionUID = 2347946353034347001L;

    private String subject;
    private String horizontal;  //控制文字的水平方向, left,center,right
    private String vertical;   //控制文字的垂直方向,top,center,bottom

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    @Override
    protected ShowSubjectClientDto clone() throws CloneNotSupportedException {
        return (ShowSubjectClientDto) super.clone();
    }
}
