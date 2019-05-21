package com.learn.productorconsumlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 注：
 * 1.虚假唤醒问题  wait()方法必须放在while循环中
 * 2.condition中await()/signal()/signalAll()分别对应 Object wait()/notify()/notifyAll()
 *
 */
public class TestProductConsumerLock {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Productor productor = new Productor(clerk);
        Consumer consumer = new Consumer(clerk);

        new Thread(productor, "生成者 A").start();
        new Thread(consumer, "消费者 B").start();

        new Thread(productor, "生成者 C").start();
        new Thread(consumer, "消费者 D").start();
    }
}

class Clerk {
    private int product = 0;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    /**
     * 进货
     */
    public void get() {
        lock.lock();
        try {
            while (product >= 1) {  //为了避免虚假唤醒问题，this.wait()应该总是使用再循环中【在一个参数版本中，中断和虚假唤醒是可能的，并且该方法应该始终在循环中使用： 】
                System.out.println("产品已经满了！");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + ++product);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 卖货
     */
    public void sale() {
        lock.lock();
        try {
            while (product <= 0) { //为了避免虚假唤醒问题，this.wait()应该总是使用再循环中【在一个参数版本中，中断和虚假唤醒是可能的，并且该方法应该始终在循环中使用： 】
                System.out.println("缺货！");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + --product);
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }
}

//生产者
class Productor implements Runnable {
    private Clerk clerk;

    public Productor(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.get();
        }
    }
}

//消费者
class Consumer implements Runnable {

    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}
