package com.kunpeng.test.day11;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class Test4 {
    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        System.out.println(queue.offer(1));
        System.out.println(queue.offer(1));
        System.out.println(queue.offer(1));
    }
}
