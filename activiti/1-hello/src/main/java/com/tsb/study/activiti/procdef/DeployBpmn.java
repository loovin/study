package com.tsb.study.activiti.procdef;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;

/**
 * 可以自己添加流程图
 */
public class DeployBpmn {
    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService rs = engine.getRepositoryService();
        DeploymentBuilder builder = rs.createDeployment();
        builder.addClasspathResource("first.bpmn");//.addClasspathResource("first.png");
        builder.deploy();
    }
}
