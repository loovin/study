package com.tsb.study.activiti.demo.oa.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class TomcatConfig {
    @Bean
    public EmbeddedServletContainerFactory embeddedServletContainerFactory() {
        ConfigurableEmbeddedServletContainer factory = new TomcatEmbeddedServletContainerFactory();
        // 请自行优化目录，可写配置参数
        factory.setDocumentRoot(new File("F:\\IT_Technology\\workspace\\study\\activiti\\demo\\oa\\src\\main\\webapp\\"));
        return (EmbeddedServletContainerFactory) factory;
    }
}
