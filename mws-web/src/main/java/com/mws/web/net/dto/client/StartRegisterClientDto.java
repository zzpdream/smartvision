package com.mws.web.net.dto.client;

import com.mws.web.net.dto.BaseDto;

/**
 * Created by ranfi on 3/30/16.
 */
public class StartRegisterClientDto extends BaseDto implements  Cloneable {

    private static final long serialVersionUID = 344690350968724004L;

    private String title;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    protected StartRegisterClientDto clone() throws CloneNotSupportedException {
        return (StartRegisterClientDto)super.clone();
    }
}
