package com.tsb.study.activiti.gateway;

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

public class EventTest {

    public static void main(String[] args) throws Exception {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 存储服务
        RepositoryService rs = engine.getRepositoryService();
        // 运行时服务
        RuntimeService runService = engine.getRuntimeService();
        // 任务服务
        TaskService taskService = engine.getTaskService();
        Deployment dep = rs.createDeployment().addClasspathResource("gateway/event.bpmn").deploy();
        ProcessDefinition pd = rs.createProcessDefinitionQuery().deploymentId(dep.getId()).singleResult();
       
        
        ProcessInstance pi = runService.startProcessInstanceById(pd.getId());
        
        
        // 发送消息
        runService.signalEventReceived("mySignal");
        // 查询当前任务
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId())
                .singleResult();
        System.out.println("当前任务：" + task.getName());
        
        
        
        // 第二条流程实例，不发送消息，只等待定时器事件的触发
        ProcessInstance pi2 = runService.startProcessInstanceById(pd.getId());
        
        Thread.sleep(10000);
        
        task = taskService.createTaskQuery().processInstanceId(pi2.getId())
                .singleResult();
        System.out.println("当前任务：" + task.getName());

    }

}
