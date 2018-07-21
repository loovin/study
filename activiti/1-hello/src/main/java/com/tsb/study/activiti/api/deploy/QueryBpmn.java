package com.tsb.study.activiti.api.deploy;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

import java.io.InputStream;

/**
 * act_re_deployment
 * act_ge_bytearray
 */
public class QueryBpmn {

    public static void main(String[] args) throws Exception {
        // 创建流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 得到流程存储服务对象
        RepositoryService repositoryService = engine.getRepositoryService();
        // 部署一份流程文件
        Deployment dep = repositoryService.createDeployment()
                .addClasspathResource("first.bpmn").deploy();
        // 查询流程定义
        //查询流程定义实体
        ProcessDefinition def = repositoryService.createProcessDefinitionQuery()
                .deploymentId(dep.getId()).singleResult();
        // 查询资源文件
        InputStream is = repositoryService.getProcessModel(def.getId());
        // 读取输入流
        int count = is.available();
        byte[] contents = new byte[count];
        is.read(contents);
        String result = new String(contents);
        //输入输出结果
        System.out.println(result);

    }
}
