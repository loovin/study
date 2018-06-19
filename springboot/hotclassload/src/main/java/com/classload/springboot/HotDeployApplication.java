package com.classload.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 热部署
 */
@SpringBootApplication
public class HotDeployApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotDeployApplication.class, args);
    }
}
