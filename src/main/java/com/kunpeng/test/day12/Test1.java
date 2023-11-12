package com.kunpeng.test.day12;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

public class Test1 {
    public static void main(String[] args) {
        ByteBuffer allocate = ByteBuffer.allocate(2);
        allocate.put((byte) 1);
        allocate.put((byte) 2);
        allocate.put((byte) 3);
        allocate.flip();
        byte b = allocate.get();
        System.out.println(b);
        allocate.compact();
        allocate.put((byte) 4);
//        debug(allocate);
        allocate.flip();
        System.out.println(allocate.get());
        System.out.println(allocate.get());
        allocate.hasRemaining();
    }
    public static void debug(ByteBuffer buffer){
        System.out.println("position:"+buffer.position()+",limit:"+buffer.limit());
    }
}
