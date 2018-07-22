package com.tsb.study.activiti.api.job;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

public class SuspendJobTest {

    public static void main(String[] args) throws Exception {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 存储服务
        RepositoryService rs = engine.getRepositoryService();
        // 运行时服务
        RuntimeService runService = engine.getRuntimeService();
        // 任务服务
        TaskService taskService = engine.getTaskService();
        
        Deployment dep = rs.createDeployment().addClasspathResource("api/job/suspend_test.bpmn").deploy();
        ProcessDefinition pd = rs.createProcessDefinitionQuery().deploymentId(dep.getId()).singleResult();
        
        ProcessInstance pi = runService.startProcessInstanceById(pd.getId());
        System.out.println(pi.getId());
        
        //act_ru_timer_job 表生成定时job
        Thread.sleep(10000);
        runService.suspendProcessInstanceById(pi.getId());
        //挂起后act_ru_timer_job 表中job 删除，并将记录移到 act_ru_suspended_job
        
        Thread.sleep(10000);
        
        runService.activateProcessInstanceById(pi.getId());
        //恢复后任务重新从act_ru_suspended_job 移回act_ru_timer_job
    }

}
