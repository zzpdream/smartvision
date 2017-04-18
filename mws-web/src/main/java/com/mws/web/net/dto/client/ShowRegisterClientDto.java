package com.mws.web.net.dto.client;

import com.mws.web.net.dto.BaseDto;

/**
 * Created by ranfi on 3/30/16.
 */
public class ShowRegisterClientDto extends BaseDto implements Cloneable {

    private static final long serialVersionUID = -2722598152274937255L;

    private Integer expected;
    private Integer registered;
    private Integer leave;
    private Integer absent;


    public Integer getExpected() {
        return expected;
    }

    public void setExpected(Integer expected) {
        this.expected = expected;
    }

    public Integer getRegistered() {
        return registered;
    }

    public void setRegistered(Integer registered) {
        this.registered = registered;
    }

    public Integer getLeave() {
        return leave;
    }

    public void setLeave(Integer leave) {
        this.leave = leave;
    }

    public Integer getAbsent() {
        return absent;
    }

    public void setAbsent(Integer absent) {
        this.absent = absent;
    }


    @Override
    protected ShowRegisterClientDto clone() throws CloneNotSupportedException {
        return (ShowRegisterClientDto)super.clone();
    }
}
