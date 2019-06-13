package com.learn.inter1;


class MyData {
    public volatile int number = 0;

    public void addTo60() {
        number = 60;
    }
}

public class TestKejian {




    public static void main(String[] args) {
        MyData myData = new MyData();
//        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myData.addTo60();

            }).start();
//        }


        while (myData.number == 0) {

        }

        System.out.println(myData.number);
    }


}
