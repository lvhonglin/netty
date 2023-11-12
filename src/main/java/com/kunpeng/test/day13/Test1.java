package com.kunpeng.test.day13;

import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.TimeUnit;

public class Test1 {
    public static void main(String[] args) {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup(3);
        for(int i=0;i<10;i++) {
            eventExecutors.next().submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("开始了:"+Thread.currentThread().getName());
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("结束了:"+Thread.currentThread().getName());
                }
            });
        }



    }
}

