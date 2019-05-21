package com.learn.copyonwrite;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList/CopyOnWriteArraySet ： “写入并复制”
 * 注意：
 * 添加操作多时，效率低，因为每次添加时都会进行复制，开销比较大
 * 并发迭代操作多时，而可以选择使用
 *
 */
public class TestCopyOnWriteArrayList {

    public static void main(String[] args) {
        CopyOnWriteArrayListDemo demo = new CopyOnWriteArrayListDemo();

        for (int i = 0; i < 10; i++) {
            new Thread(demo).start();
        }

    }

}

class CopyOnWriteArrayListDemo implements Runnable {

//    private static List<String> list = Collections.synchronizedList(new ArrayList<String>());

    private static CopyOnWriteArrayList list = new CopyOnWriteArrayList();

    static {
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }

    @Override
    public void run() {
        Iterator it = list.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
            list.add("DD");
        }
    }
}
