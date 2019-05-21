package com.learn.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用于解决多线程安全问题的方式：
 * 1.synchronized 同步代码块
 * 2.synchronized 同步方法
 * 3.同步锁 Lock
 */
public class TestLock {

    public static void main(String[] args) {
        Ticket t = new Ticket();

        new Thread(t, "1号窗口").start();
        new Thread(t, "2号窗口").start();
        new Thread(t, "3号窗口").start();

    }

}

class Ticket implements Runnable {

    private int tick = 100;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        //加锁
        lock.lock();
        try {
            while (true) {
                if (tick > 0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " 完成售票，余票为： " + --tick);
                }
            }
        } finally {
            //释放锁，注：必须放在finally中，因为必须释放锁
            lock.unlock();
        }
    }
}
