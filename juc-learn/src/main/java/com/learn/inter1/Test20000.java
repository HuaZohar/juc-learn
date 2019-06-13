package com.learn.inter1;

import java.util.concurrent.atomic.AtomicInteger;

public class Test20000 {

    static int num = 0;

    static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < 1000; i1++) {
                    num++;
                    atomicInteger.getAndIncrement();
                }
            }, "Thread_" + i).start();
        }

        //需要等待上面20个线程全部执行完成，再用main线程获取最终的结果
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println("num:" + num);
        System.out.println("ato:" + atomicInteger.get());


    }
}
