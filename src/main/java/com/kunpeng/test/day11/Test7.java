package com.kunpeng.test.day11;

import java.util.concurrent.*;

public class Test7 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(2);
                return 123;
            }
        };
        Future<Integer> submit = executorService.submit(callable);
        Integer res = submit.get();
        System.out.println(res);
        System.out.println(submit.isDone());

    }
}
