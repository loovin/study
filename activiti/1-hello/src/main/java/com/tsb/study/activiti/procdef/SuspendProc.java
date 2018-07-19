package com.tsb.study.activiti.procdef;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;

/**
 * 流程被suspend 后，不能再启动实例
 */
public class SuspendProc {
    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 存储服务
        RepositoryService rs = engine.getRepositoryService();
        DeploymentBuilder builder = rs.createDeployment();
        builder.addClasspathResource("first.bpmn");
        Deployment dep = builder.deploy();

        ProcessDefinition def = rs.createProcessDefinitionQuery()
                .deploymentId(dep.getId()).singleResult();
        System.out.println("id: " + def.getId());
        rs.suspendProcessDefinitionByKey(def.getKey());
        // 将会抛出异常，因为流程定义被中止了
        RuntimeService runService = engine.getRuntimeService();
        runService.startProcessInstanceByKey(def.getKey());
    }
}
