package com.tsb.study.lambda.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2018/6/29.
 */
public class CreateStream {
    static String[] arr = {"a","b","c","1","2"};

    static void create1() {//数组
        Stream<String> stream = Stream.of(arr);
        stream.forEach(System.out::println);
    }

    static void create2(){//集合
        List<String> list = Arrays.asList(arr);
        Stream<String> stream = list.stream();
        stream.forEach(System.out::println);
    }

    static void create3(){//generate 方法创建，无止境的流，可通过limit截取
        Stream<String> stream = Stream.generate(()-> "a");
        stream.limit(10).forEach(System.out::println);
    }

    static void create4(){//iterate 方法创建，无止境的流，可通过limit截取
        Stream<Integer> stream = Stream.iterate(1,x -> x +1);
        stream.limit(10).forEach(System.out::println);
    }

    static void create5() throws IOException {//通过其他 api 创建
        String str = "abcdefg12345";
        IntStream stream = str.chars();
        stream.forEach(System.out::println);

        Files.lines(Paths.get("d:\\北京鑫世通.xml")).forEach(System.out::println);
    }

    public static void main(String str[]) {
        try {
            create4();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
