package com.mws.web.net.dto;

import java.util.List;

/**
 * Created by ranfi on 2/28/16.
 */
public class LogoDto extends BaseDto {

    private List<String> contents;


    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }
}
