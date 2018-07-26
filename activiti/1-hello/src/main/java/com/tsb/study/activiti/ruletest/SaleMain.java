package com.tsb.study.activiti.ruletest;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class SaleMain {

    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 存储服务
        RepositoryService rs = engine.getRepositoryService();
        // 运行时服务
        RuntimeService runService = engine.getRuntimeService();
        // 任务服务
        TaskService taskService = engine.getTaskService();
        Deployment dep = rs.createDeployment()
                .addClasspathResource("ruletest/sale.bpmn")
                .addClasspathResource("ruletest/sale.drl").deploy();
        ProcessDefinition pd = rs.createProcessDefinitionQuery()
                .deploymentId(dep.getId()).singleResult();
        ProcessInstance pi = runService.startProcessInstanceById(pd.getId());
        
        // 完成第一个任务并设置销售参数
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        // 设置参数
        Map<String, Object> vars = new HashMap<String, Object>();
        Member m = new Member();
        m.setIdentity("gold");
        m.setAmount(100);
        vars.put("member", m);
        taskService.complete(task.getId(), vars);
    }

}
