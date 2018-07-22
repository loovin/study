package com.tsb.study.activiti.api.task;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.UUID;

/**
 * 为方便测试，直接新建任务，这样导致任务没有与流程挂钩，实际应用中任务应该对应有流程
 * 表：act_ru_identitylink
 */
public class TaskCandidate {
    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        TaskService ts = engine.getTaskService();
        IdentityService is = engine.getIdentityService();
        // 创建任务
        String taskId = UUID.randomUUID().toString();
        Task task = ts.newTask(taskId);
        task.setName("测试任务");
        ts.saveTask(task);
        //  创建用户
        String userId = UUID.randomUUID().toString();
        User user = is.newUser(userId);
        user.setFirstName("angus");
        is.saveUser(user);
        // 设置任务的候选用户组
        ts.addCandidateUser(taskId, userId);


        List<Task> tasks = ts.createTaskQuery().taskCandidateUser(userId).list();
        System.out.println(userId + " 这个用户有权限处理的任务有：");
        for(Task t : tasks) {
            System.out.println(t.getName());
        }
    }
}
