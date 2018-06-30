package com.tsb.study.lambda.stream;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 中间操作
 */
public class MiddleOper {
    static void oper1(){//过滤操作
        Stream<Integer> stream = Arrays.asList(1,2,3,4,5).stream().filter(x -> {
            System.out.println(x+"：执行过滤操作。。。。。。。。");
            return x%2==0;
        });
        System.out.println("----------------开始终止操作");
        stream.forEach(System.out::println);
    }

    public static void main(String str[]){
        oper1();
    }
}
