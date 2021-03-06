package com.tsb.study.activiti.api.flow;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
/**
 * 信号捕获事件 runService.signalEventReceived("testSignal");
 */
public class SignalCatchEvent {
    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 存储服务
        RepositoryService rs = engine.getRepositoryService();
        // 运行时服务
        RuntimeService runService = engine.getRuntimeService();
        // 任务服务
        TaskService taskService = engine.getTaskService();
        // 部署
        Deployment dep = rs.createDeployment()
                .addClasspathResource("api/flow/SignalCatchEvent.bpmn").deploy();
        ProcessDefinition pd = rs.createProcessDefinitionQuery()
                .deploymentId(dep.getId()).singleResult();
        // 启动流程
        ProcessInstance pi = runService.startProcessInstanceById(pd.getId());
        // 查当前的子执行流（只有一个）
        Execution exe = runService.createExecutionQuery()
                .processInstanceId(pi.getId()).onlyChildExecutions()
                .singleResult();

        System.out.println(pi.getId() + ", 当前节点：" + exe.getActivityId());

        runService.signalEventReceived("testSignal");

        exe = runService.createExecutionQuery()
                .processInstanceId(pi.getId()).onlyChildExecutions()
                .singleResult();

        System.out.println(pi.getId() + ", 当前节点：" + exe.getActivityId());
    }
}
