package com.tsb.study.concurrency.lambda.stream;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 中间操作
 */
public class MiddleOper {
    static void filter(){//过滤操作
        Stream<Integer> stream = Arrays.asList(1,2,3,4,5).stream().filter(x -> {
            System.out.println(x+"：执行过滤操作。。。。。。。。");
            return x%2==0;
        });
        System.out.println("----------------开始终止操作");
        stream.forEach(System.out::println);
    }

    static void sum(){//sum
        int result = Arrays.asList(1,2,3,4,5,6).stream().filter(x->x%2==0).mapToInt(x->x).sum();
        System.out.println(result);
    }

    static void max(){
        int max = Arrays.asList(3,5,1,9,6,7).stream().max((x,y)-> x-y).get();
        System.out.println(max);
    }

    static void findAny(){
        for(int i=0;i<20;i++) {
            int r = Arrays.asList(3, 5, 6, 1, 4, 2, 9, 8, 7).stream().filter(x -> x % 2 == 0).findAny().get();
            System.out.println(r);
        }
    }

    static void collect(){
        List<Integer> list = Stream.iterate(1, x->x+1).limit(50).filter(x->x%2==0).collect(Collectors.toList());
        System.out.println(list);
    }

    static void distinct(){
        Arrays.asList(3, 5, 6, 5, 4, 2, 3, 8, 7).stream().distinct().forEach(System.out::println);
    }

    static void page(){//分页，获取20-30之间的数据
        Stream.iterate(1,x->x+1).limit(50).skip(10).limit(10).forEach(System.out::println);
    }

    static void map(){
        String[] arr = {"2","3","4","1","2"};
        int r = Stream.of(arr).mapToInt(Integer::parseInt).sum();
        System.out.println(r);
    }

    static void peek(){//查看中间过程每个元素的值
        String[] arr = {"2","3","4","1","2"};
        int r = Stream.of(arr).peek(System.out::println).mapToInt(Integer::parseInt).sum();
        System.out.println(r);
    }

    public static void main(String str[]){
        peek();
    }
}
