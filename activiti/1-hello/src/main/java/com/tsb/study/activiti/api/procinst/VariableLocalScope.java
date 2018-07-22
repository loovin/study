package com.tsb.study.activiti.api.procinst;
import java.util.List;

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
/**
 * 流程实例的本地参数scope
 */
public class VariableLocalScope {
    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 存储服务
        RepositoryService rs = engine.getRepositoryService();
        // 运行时服务
        RuntimeService runService = engine.getRuntimeService();
        // 任务服务
        TaskService taskService = engine.getTaskService();
        // 部署
        Deployment dep = rs.createDeployment().addClasspathResource("api/procinst/variable.bpmn").deploy();
        ProcessDefinition pd = rs.createProcessDefinitionQuery().deploymentId(dep.getId()).singleResult();
        // 启动流程
        ProcessInstance pi = runService.startProcessInstanceById(pd.getId());


        List<Task> tasks = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
        for(Task task : tasks) {
            Execution exe = runService.createExecutionQuery()
                    .executionId(task.getExecutionId()).singleResult();
            if("TaskA".equals(task.getName())) {
                runService.setVariableLocal(exe.getId(), "taskVarA", "varA");
            } else {
                runService.setVariable(exe.getId(), "taskVarB", "varB");
            }
        }


        for(Task task : tasks) {
            taskService.complete(task.getId());
        }


        Task taskC = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        System.out.println(runService.getVariable(pi.getId(), "taskVarA"));
        System.out.println(runService.getVariable(pi.getId(), "taskVarB"));


        System.out.println(pi.getId());
    }
}
