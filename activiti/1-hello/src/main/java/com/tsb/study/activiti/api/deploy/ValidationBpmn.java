package com.tsb.study.activiti.api.deploy;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;

/**
 * 默认会验证格式、流程逻辑，
 * 如果disableSchemaValidation 则不会验证格式
 * disableBpmnValidation 则不会验证流程逻辑
 */
public class ValidationBpmn {

    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 存储服务
        RepositoryService rs = engine.getRepositoryService();
        
        DeploymentBuilder builder = rs.createDeployment();
        builder.addClasspathResource("error/schema_error.bpmn");
        builder.disableSchemaValidation();
        builder.disableBpmnValidation();
        builder.deploy();
    }
}
