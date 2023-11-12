package com.kunpeng.test.day11;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test5 {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    System.out.println("异常了");
                }
                System.out.println("over");
            }
        });
        TimeUnit.SECONDS.sleep(1);
        executorService.shutdown();
        boolean shutdown = executorService.isShutdown();
        System.out.println(shutdown);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("ee");
            }
        });

    }
}
