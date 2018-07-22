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
 * 任务持有人，act_ru_task.owner_ 字段
 */
public class TaskOwner {
    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        TaskService ts = engine.getTaskService();
        IdentityService is = engine.getIdentityService();
        // 创建任务
        String taskId = UUID.randomUUID().toString();
        Task task = ts.newTask(taskId);
        task.setName("test owner task");
        ts.saveTask(task);
        //  创建用户
        String userId = UUID.randomUUID().toString();
        User user = is.newUser(userId);
        user.setFirstName("angus");
        is.saveUser(user);
        // 设置任务的候选用户组
        ts.setOwner(taskId, userId);
        //ts.setAssignee();

        // 根据用户来查询他所持有的任务
        List<Task> tasks = ts.createTaskQuery().taskOwner(userId).list();
        for(Task t : tasks) {
            System.out.println(t.getName());
        }
    }
}
