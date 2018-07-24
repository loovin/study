package com.tsb.study.activiti.subprocess;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class AdTest {

    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 存储服务
        RepositoryService rs = engine.getRepositoryService();
        // 运行时服务
        RuntimeService runService = engine.getRuntimeService();
        // 任务服务
        TaskService taskService = engine.getTaskService();
        Deployment dep = rs.createDeployment().addClasspathResource("subprocess/Ad.bpmn")
                .deploy();
        ProcessDefinition pd = rs.createProcessDefinitionQuery()
                .deploymentId(dep.getId()).singleResult();
        ProcessInstance pi = runService.startProcessInstanceById(pd.getId());

        Execution exe = runService.createExecutionQuery()
                .processInstanceId(pi.getId()).activityId("subprocess1").singleResult();
        
        System.out.println(exe.getId());

        // 让流程到达任务2
        runService.executeActivityInAdhocSubProcess(exe.getId(), "usertask2");
        // 查找任务2并完成
        Task subProcessTask2 = taskService.createTaskQuery()
                .processInstanceId(pi.getId())
                .taskDefinitionKey("usertask2").singleResult();
        taskService.complete(subProcessTask2.getId());
        
        // 完成特别子流程
        runService.completeAdhocSubProcess(exe.getId());
        
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        System.out.println("当前任务：" + task.getName());
    }

}
