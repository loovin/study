package com.tsb.study.guava.concurrent.e01_demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JdkFuture {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        System.out.println("Ready");
        Future strFuture = executor.submit(new TaskTest());
        System.out.println("Give the future");

        Thread.sleep(5000);

        System.out.println("Get the future : " + strFuture.get());
        System.out.println("End");
        executor.shutdown();
    }

    public static class TaskTest implements Callable {

        public String call() throws Exception {
            Thread.sleep(3000);
            System.out.println("callable returned.....");
            return "Hello World!";
        }
    }
}
