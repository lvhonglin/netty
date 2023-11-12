package com.kunpeng.test.day8;

public class PingMessage extends Message{
    public PingMessage() {
    }
    @Override
    public String msg() {
        return "ping";
    }

    @Override
    public String name() {
        return null;
    }
}
