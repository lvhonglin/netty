package com.kunpeng.test.day8;

import java.io.Serializable;

public class RealMessage extends Message{
    private String msg;
    private String name;

    public RealMessage() {
    }

    public RealMessage(String msg, String name) {
        this.msg = msg;
        this.name = name;
    }

    @Override
    public String msg() {
        return msg;
    }

    @Override
    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
