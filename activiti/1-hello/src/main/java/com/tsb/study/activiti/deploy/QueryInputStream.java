package com.tsb.study.activiti.deploy;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * 保存到act_ge_bytearray 表里的记录进行查询
 */
public class QueryInputStream {

    public static void main(String arts[]) throws Exception {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 存储服务
        RepositoryService rs = engine.getRepositoryService();

        DeploymentBuilder builder = rs.createDeployment();
        builder.addClasspathResource("log4j.properties");
        Deployment deployment = builder.deploy();

        InputStream is = rs.getResourceAsStream(deployment.getId(), "log4j.properties");
        int count = is.available();
        byte[] contents = new byte[count];
        is.read(contents);
        String result = new String(contents);
        //输入结果
        System.out.println(result);
    }
}
