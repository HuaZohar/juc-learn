package com.learn.readwrite;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 1.ReadWriteLock:读写锁
 *
 * 写写、读写  需要互斥
 *
 * 读读 不需要互斥
 */
public class TestReadWriteLock {

    public static void main(String[] args) {

        final ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                readWriteLockDemo.set(11);
            }
        }, "Write:").start();

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    readWriteLockDemo.get();
                }
            }).start();
        }
    }

}

class ReadWriteLockDemo {

    private int num = 0;

    private ReadWriteLock lock = new ReentrantReadWriteLock();


    //读
    public void get() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " : " + num);
        } finally {
            lock.readLock().unlock();
        }
    }

    //写
    public void set(int value) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName());
            this.num = value;
        } finally {
            lock.writeLock().unlock();
        }

    }

}
