package com.learn.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建执行线程的方式三： 实现callable接口。
 * 相比较实现Runnable接口的方式，方法可以有返回值，并且可以抛出异常
 *
 * 实现callable，也可以作为闭锁，类似于CountDownLatch类
 */
public class TestCallable {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CallableDemo callableDemo = new CallableDemo();

        //执行Callable方式，需要FutureTask实现类的支持，用于接收运算结果
        FutureTask<Integer> result = new FutureTask<>(callableDemo);

        new Thread(result).start();

        //接收线程运算结果
        Integer integer = result.get();

        System.out.println("-------------------");

        System.out.println(integer);

    }

}

class CallableDemo implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= 10000; i++) {
            sum += i;
        }
        return sum;
    }
}
