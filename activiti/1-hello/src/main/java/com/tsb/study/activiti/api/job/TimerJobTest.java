package com.tsb.study.activiti.api.job;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * 定时任务会产生job，表：act_ru_timer_job
 * 本例中定时任务为1分钟，1分钟后定时任务将执行，同时表act_ru_timer_job中的对应的记录将删除
 */
public class TimerJobTest {

    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 存储服务
        RepositoryService rs = engine.getRepositoryService();
        // 运行时服务
        RuntimeService runService = engine.getRuntimeService();
        // 任务服务
        TaskService taskService = engine.getTaskService();
        
        Deployment dep = rs.createDeployment().addClasspathResource("api/job/timer_test.bpmn").deploy();
        ProcessDefinition pd = rs.createProcessDefinitionQuery().deploymentId(dep.getId()).singleResult();
        
        ProcessInstance pi = runService.startProcessInstanceById(pd.getId());
        System.out.println(pi.getId());
    }

}
