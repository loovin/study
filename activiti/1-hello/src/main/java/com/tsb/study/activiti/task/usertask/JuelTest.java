package com.tsb.study.activiti.task.usertask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class JuelTest {

    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 存储服务
        RepositoryService rs = engine.getRepositoryService();
        // 运行时服务
        RuntimeService runService = engine.getRuntimeService();
        IdentityService is = engine.getIdentityService();
        // 任务服务
        TaskService taskService = engine.getTaskService();

//        User userA = is.newUser("userA");
//        userA.setFirstName("Angus");
//        is.saveUser(userA);
//        
//        User userB = is.newUser("userB");
//        userB.setFirstName("Angus");
//        is.saveUser(userB);

        Deployment dep = rs.createDeployment()
                .addClasspathResource("task/usertask/juel.bpmn").deploy();
        ProcessDefinition pd = rs.createProcessDefinitionQuery()
                .deploymentId(dep.getId()).singleResult();
        
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("authService", new AuthService());

        ProcessInstance pi = runService.startProcessInstanceById(pd.getId(), vars);
        
        // 查询各个用户下面有权限看到的任务
        
        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser("userA").list();
        System.out.println("userA 用户，可以认领的任务：" + tasks.size() + "---" + tasks.get(0).getName());
        
        tasks = taskService.createTaskQuery().taskCandidateUser("userB").list();
        System.out.println("userB 用户，可以认领的任务：" + tasks.size() + "---" + tasks.get(0).getName());
    }
}
