package com.kunpeng.test.day8;

import java.io.Serializable;

public abstract class Message implements Serializable {
    public abstract String msg();
    public abstract String name();

}
