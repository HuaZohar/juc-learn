package com.learn.volatiledemo;

/**
 * volatile 关键字：当多个线程操作共享数据时，可以保证内存中数据可见
 *                  相较于synchronized是一种轻量级的同步策略
 *
 * 注意：
 * volatile 不具备“互斥性”
 * volatile 不能保证变量的“原子性”
 */
public class volatileTest {

    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo();
        new Thread(threadDemo).start();

        while (true) {
            if (threadDemo.isFlag()) {
                System.out.println("------------------------");
                break;
            }
        }
    }
}

class ThreadDemo implements Runnable {
    private volatile boolean flag = false;
    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
    }
    public boolean isFlag() {
        return flag;
    }
}
