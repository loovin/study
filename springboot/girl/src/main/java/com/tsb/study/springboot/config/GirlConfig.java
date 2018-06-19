package com.tsb.study.springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/1/13.
 */
@Component
@ConfigurationProperties(prefix = "girl")
public class GirlConfig {
    private int hight;
    private int weight;

    @Override
    public String toString() {
        return "hight="+hight +", weight="+weight;
    }

    public int getHight() {
        return hight;
    }

    public void setHight(int hight) {
        this.hight = hight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
