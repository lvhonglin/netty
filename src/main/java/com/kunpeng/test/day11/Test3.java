package com.kunpeng.test.day11;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class Test3 {
    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingDeque<Integer> queue = new LinkedBlockingDeque<>(2);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(queue.offer(1));
                System.out.println(queue.offer(2));
                //offer当queue满的时候会返回false
                boolean offer3 = queue.offer(3);
                System.out.println(offer3);
                try {
                    System.out.println(queue.take());
                    System.out.println(queue.take());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Integer take = queue.take();
                    System.out.println(take);
                } catch (InterruptedException e) {
                    System.out.println("获取第三个的时候，获取不到所以阻塞了，" +
                            "外部调用interrupt导致抛异常了");
                }
            }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();
    }
}
