package com.learn.alternateprint;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestABCAlternate {

    public static void main(String[] args) {

        final AlternateDemo alternateDemo = new AlternateDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    alternateDemo.loopA(i);
                }
            }
        }, "A").start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    alternateDemo.loopB(i);
                }
            }
        }, "B").start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    alternateDemo.loopC(i);
                }
            }
        }, "C").start();
    }

}

class AlternateDemo {

    private int number = 1;
    private Lock lock = new ReentrantLock();

    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    /**
     * A线程
     *
     * @param totalLoop 循环几轮
     */
    public void loopA(int totalLoop) {
        //加锁
        lock.lock();
        try {
            //如果不是当前线程标识，则等待
            if (number != 1) {
                condition1.await();
            }
            //打印
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            //第二个线程标识
            number = 2;
            //唤醒下一个线程
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放锁
            lock.unlock();
        }
    }


    /**
     * B线程
     *
     * @param totalLoop 循环几轮
     */
    public void loopB(int totalLoop) {
        //加锁
        lock.lock();
        try {
            //如果不是当前线程标识，则等待
            if (number != 2) {
                condition2.await();
            }
            //打印
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            //第二个线程标识
            number = 3;
            //唤醒下一个线程
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放锁
            lock.unlock();
        }
    }


    /**
     * B线程
     *
     * @param totalLoop 循环几轮
     */
    public void loopC(int totalLoop) {
        //加锁
        lock.lock();
        try {
            //1.如果不是当前线程标识，则等待
            if (number != 3) {
                condition3.await();
            }

            //2.打印
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            //第二个线程标识
            number = 1;
            //3.唤醒下一个线程
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放锁
            lock.unlock();
        }
    }
}
