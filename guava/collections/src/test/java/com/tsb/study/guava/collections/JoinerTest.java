package com.tsb.study.guava.collections;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/3/24.
 */
public class JoinerTest {
    private final List<String> stringList = Arrays.asList(
            "Google","guava","java","scala","kafka"
    );

    private final List<String> stringListWithNull = Arrays.asList(
            "Google","guava","java","scala",null
    );

    @Test
    public void testJoinOnJoin(){
        String result = Joiner.on(";").join(stringList);
        System.out.println(result);
    }

    @Test
    public void testJoinOnJoinWithNull(){
        String result = Joiner.on(";").skipNulls().join(stringListWithNull);
        System.out.println(result);
    }
}
