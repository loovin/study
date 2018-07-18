package com.tsb.study.concurrency.lambda.hello;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2018/6/28.
 */
public class Test {
    public static void main(String s[]) throws Exception {
        Too t = i -> i+5;
        System.out.println(t.getValue(3));
    }

    interface Too{
        int getValue(int i);
    }
}
