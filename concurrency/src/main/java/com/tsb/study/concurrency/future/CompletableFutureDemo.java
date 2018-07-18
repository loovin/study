package com.tsb.study.concurrency.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2018/7/14.
 */
public class CompletableFutureDemo {

    static void test() throws Exception {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        future.complete(50);
        System.out.println(future.get());
    }

    static void test1() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 200;
        });
        System.out.println("wait for result");
        System.out.println(future.get());
    }

    public static void main(String str[]){
        try {
            test1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
