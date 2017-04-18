package com.mws.web.net.dto.client;

import com.mws.web.net.dto.BaseDto;

/**
 * Created by ranfi on 3/30/16.
 */
public class UpdateSeatClientDto extends BaseDto {

    private static final long serialVersionUID = 5138949208431177636L;

    private Integer terminalType;

    public Integer getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(Integer terminalType) {
        this.terminalType = terminalType;
    }
}
