package com.kunpeng.test.day11;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test1 {
    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("myGroup");
        threadGroup.setMaxPriority(5);
        Thread thread = new Thread(threadGroup, new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.setPriority(10);
        //线程的优先级受group的最大优先级影响，不会超过group的最大优先级，
        //group的最大优先级默认是10
        System.out.println(thread.getPriority());
        ThreadGroup threadGroup1 = Thread.currentThread().getThreadGroup();
        ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<>();

        integerThreadLocal.get();
    }
}
