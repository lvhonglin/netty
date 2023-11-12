package com.kunpeng.test.day6;

import java.io.Serializable;

public abstract class Message implements Serializable {
    public abstract byte getType();
    public abstract int getId();

}
