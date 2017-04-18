package com.mws.web.net.dto;

/**
 * Created by ranfi on 2/28/16.
 */
public class RegisterDto {

    private Integer expected;
    private Integer registered;
    private Integer askForLeave;
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

    public Integer getAskForLeave() {
        return askForLeave;
    }

    public void setAskForLeave(Integer askForLeave) {
        this.askForLeave = askForLeave;
    }

    public Integer getAbsent() {
        return absent;
    }

    public void setAbsent(Integer absent) {
        this.absent = absent;
    }
}
