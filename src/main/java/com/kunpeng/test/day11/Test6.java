package com.kunpeng.test.day11;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test6 {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newCachedThreadPool();
        for(int i=0;i<10;i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        System.out.println("被中止了");
                    }
                    System.out.println("over");
                }
            });
        }
        TimeUnit.SECONDS.sleep(1);
        executorService.shutdownNow();

    }
}
