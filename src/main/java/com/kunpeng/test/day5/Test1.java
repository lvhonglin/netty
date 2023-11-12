package com.kunpeng.test.day5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class Test1 {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        System.out.println(buffer);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<300;i++){
            stringBuilder.append('a');
        }
        byte[] tmp=stringBuilder.toString().getBytes();
        System.out.println(tmp.length);
        buffer.writeBytes(tmp);
        System.out.println(buffer);
    }
}
