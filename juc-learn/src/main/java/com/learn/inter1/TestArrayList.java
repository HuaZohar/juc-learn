package com.learn.inter1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class TestArrayList {

    public static void main(String[] args) {

        /**
         * 3种解决方案
         * 1.new Vector<></>()
         * 2.Collections.synchronizedList(new ArrayList<>());
         * 3.CopyOnWriteArrayList<>();
         *
         *
         * CopyOnWirteArrayList<>().add()源码如下：
         *
         *     public boolean add(E e) {
         *         final ReentrantLock lock = this.lock;
         *         lock.lock();
         *         try {
         *             Object[] elements = getArray();
         *             int len = elements.length;
         *             Object[] newElements = Arrays.copyOf(elements, len + 1);
         *             newElements[len] = e;
         *             setArray(newElements);
         *             return true;
         *         } finally {
         *             lock.unlock();
         *         }
         *     }
         */
        List<Integer> list = new CopyOnWriteArrayList<>();//Collections.synchronizedList(new ArrayList<>());//new Vector<>();//new ArrayList<>();

        for (int i = 0; i < 2000; i++) {
            new Thread(() -> {
                list.add(new Random().nextInt(10000));
                System.out.println(list.toString());
            }).start();
        }


        new ArrayList<>().add(1);





    }
}
