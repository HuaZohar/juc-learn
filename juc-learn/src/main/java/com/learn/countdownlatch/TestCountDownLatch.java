package com.learn.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch：闭锁，在完成某些操作运算时，只有其他的所有线程的运算全部完成，当前运算才继续执行
 *
 * new CountDownLatch(5)  表示等待5个线程执行完毕，再做别的事情，每一个线程执行完成就递减1
 */
public class TestCountDownLatch {

    public static void main(String[] args) {
        //new CountDownLatch(5)  表示等待5个线程执行完毕，再做别的事情，每一个线程执行完成就递减1
        CountDownLatch countDownLatch = new CountDownLatch(5);

        CountDownLatchDemo countDownLatchDemo = new CountDownLatchDemo(countDownLatch);


        long begin = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            new Thread(countDownLatchDemo).start();
        }
        //线程还没有执行完成需要等待
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();

        System.out.println("spend time : " + (end - begin));

    }

}

class CountDownLatchDemo implements Runnable {

    private CountDownLatch latch;

    public CountDownLatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                for (int i = 0; i < 50000; i++) {
                    if (i % 2 == 0) {
                        System.out.println(i);
                    }
                }
            } finally {
                //线程执行完毕，递减1，注：必须放在finally中（因为必须要递减）
                latch.countDown();
            }
        }

    }
}
