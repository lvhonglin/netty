package com.kunpeng.test.day11;

import java.util.concurrent.TimeUnit;

public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup threadGroup = new ThreadGroup("myGroup");
        for(int i=0;i<10;i++) {
            Thread thread = new Thread(threadGroup, new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName()+"被销毁了");
                    }
                }
            });
            thread.start();
        }

        TimeUnit.SECONDS.sleep(1);
        //因为当前threadGroup中还有活跃线程所以报错
        threadGroup.destroy();
    }
}
