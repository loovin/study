package com.tsb.study.concurrency.lambda.stream;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

/**
 * 并行流pa
 */
public class ConcurStream {
    public static void main(String str[]){
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","10");
        int max = Stream.iterate(1,x->x+1).parallel().limit(200).peek(x->{
            System.out.println(Thread.currentThread().getName());
        }).max(Integer::compare).get();
        System.out.println(max);
    }
}
