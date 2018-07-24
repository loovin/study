package com.tsb.study.activiti.subprocess;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

/**
 * 调用子流程
 */
public class CallActivityTest {

    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 存储服务
        RepositoryService rs = engine.getRepositoryService();
        // 运行时服务
        RuntimeService runService = engine.getRuntimeService();
        // 任务服务
        TaskService taskService = engine.getTaskService();
        Deployment dep = rs.createDeployment()
                .addClasspathResource("subprocess/CallActivitySub.bpmn")
                .addClasspathResource("subprocess/Offwork.bpmn").deploy();
        ProcessDefinition pd = rs.createProcessDefinitionQuery()
                .deploymentId(dep.getId()).processDefinitionKey("myProcess").singleResult();
        ProcessInstance pi = runService.startProcessInstanceById(pd.getId());
        
        System.out.println(pi.getId());
        
        
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        System.out.println("当前任务名称：" + task.getName());
        
        taskService.complete(task.getId());
        
        ProcessInstance piSub = runService.createProcessInstanceQuery().superProcessInstanceId(pi.getId()).singleResult();
        task = taskService.createTaskQuery().processInstanceId(piSub.getId()).singleResult();
        
        System.out.println("当前任务名称：" + task.getName());
    }

}
