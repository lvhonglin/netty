package com.kunpeng.test.day1;

import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.TimeUnit;

public class Test1 {
    static class Test implements Runnable{
        private String msg;
        public Test(String msg) {super();this.msg = msg;}
        @Override
        public void run() {
            try {Thread.sleep(1000);} catch (InterruptedException e) {throw new RuntimeException(e);}
            System.out.println(msg+":"+Thread.currentThread().getName());
        }
    }
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());

       group.next().scheduleAtFixedRate(new Test("msg1"),0,1, TimeUnit.SECONDS);
       group.next().scheduleAtFixedRate(new Test("msg2"),0,1, TimeUnit.SECONDS);
       group.next().scheduleAtFixedRate(new Test("msg3"),0,1, TimeUnit.SECONDS);
    }
}
