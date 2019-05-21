package com.learn.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1.i++的原子性问题：i++的操作实际上分为三步骤“读-改-写”
 *
 * 2.使用volatile ，不保证原子性，无法解决安全性问题
 *
 * 3.原子变量：jdk1.5后java.util.concurrent.atomic包下提供了常用的原子变量：
 *
 * 以AtomicInteger为例
 * 1)volatile 保证内存可见性
 * 2)CAS（Compare-And-Swap）算法保证数据的原子性
 *      CAS算法是硬件对于并发操作共享数据的支持
 *      CAS包含了三个操作：
 *          内存值 V
 *          预估值 A
 *          更新值 B
 *          当且仅当V==A时，V=B.否则，将不做任何操作
 */
public class TestAtomicDemo {

    public static void main(String[] args) {
        AtomicDemo atomicDemo = new AtomicDemo();

        for (int i = 0; i < 10; i++) {
            new Thread(atomicDemo).start();
        }


    }


}

class AtomicDemo implements Runnable {

    private AtomicInteger serialNum = new AtomicInteger();

//    private volatile int serialNum = 0;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getSerialNum());
    }


    private int getSerialNum() {
        return serialNum.getAndIncrement();
    }
}
