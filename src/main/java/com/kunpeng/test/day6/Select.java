package com.kunpeng.test.day6;

public class Select extends Message{
    @Override
    public byte getType() {
        return 1;
    }

    @Override
    public int getId() {
        return 1;
    }
}
