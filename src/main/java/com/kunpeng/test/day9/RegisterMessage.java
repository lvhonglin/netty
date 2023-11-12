package com.kunpeng.test.day9;

public class RegisterMessage extends Message {
    private String msg;
    private String name;

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
