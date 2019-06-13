package com.learn.inter1;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class TestSet {

    /**
     * set
     * 1.线程不安全，HashSet底层其实是HashMap,值保存在key键上面，value值为常量，如 map.put(e, PRESENT)==null;
     *
     * 2.CopyOnWriteArraySet底层是CopyOnWriteArrayList
     *
     * 3.set线程不安全解决方案
     * 1）Collections.synchronizedSet(new HashSet<>());
     * 2) new CopyOnWriteArraySet();
     *
     *
     *
     *
     * @param args
     */

    public static void main(String[] args) {
        Set<String> sets = new CopyOnWriteArraySet();//Collections.synchronizedSet(new HashSet<>());//new HashSet<>();


        for (int i = 0; i < 2000; i++) {
            final int i1 = i;
            new Thread(() -> {
                sets.add("test " + i1);
                System.out.println(sets.toString());
            }).start();
        }


        new HashSet<>().add(1);
    }
}
