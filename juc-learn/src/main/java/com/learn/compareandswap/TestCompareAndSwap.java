package com.learn.compareandswap;

import java.util.Random;

/**
 * 模拟CAS算法
 * <p>
 * 预估值  == 当前内存中的值
 */
public class TestCompareAndSwap {

    public static void main(String[] args) {
        final CompareAndSwapDemo compareAndSwapDemo = new CompareAndSwapDemo();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //预估值
                    int expectedVale = compareAndSwapDemo.get();
                    boolean b = compareAndSwapDemo.compareAndSet(expectedVale, new Random(100).nextInt());
                    System.out.println(b);
                }
            }).start();

        }
    }
}

class CompareAndSwapDemo {

    private int value;

    public synchronized int get() {
        return value;
    }

    /**
     * 比较
     *
     * @param exceptedValue 预估值
     * @param newValue      新值
     * @return
     */
    public synchronized int compareAndSwap(int exceptedValue, int newValue) {
        int oldValue = value;
        if (exceptedValue == oldValue) {
            value = newValue;
        }
        return oldValue;
    }

    /**
     * 设置
     *
     * @param exceptedValue 预估值
     * @param newValue      新值
     * @return
     */
    public synchronized boolean compareAndSet(int exceptedValue, int newValue) {
        return exceptedValue == compareAndSwap(exceptedValue, newValue);
    }


}
