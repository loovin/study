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
 * 用户代理人，表act_ru_task.ASSIGNEE_
 * setAssignee和claim两个的区别是在认领任务时，
 claim会检查该任务是否已经被认领，如果被认领则会抛出ActivitiTaskAlreadyClaimedException
 而setAssignee不会进行这样的检查，其他方面两个方法效果一致。
 ------
 setOwner和setAssignee的区别在于
 setOwner实在代理任务时使用，代表着任务的归属者，而这时，setAssignee代表的时代理办理者，
 举个例子来说，公司总经理现在有个任务taskA，去核实一下本年度的财务报表，他现在又很忙没时间，于是将该任务委托给其助理进行办理，此时，就应该这么做：
 taskService.setOwner(taskA.getId(), 总经理.getId());
 taskService.setAssignee/claim(taskA.getId(), 助理.getId());
 */
public class TaskClaimAssignee {
    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        TaskService ts = engine.getTaskService();
        IdentityService is = engine.getIdentityService();
        // 创建任务
        String taskId = UUID.randomUUID().toString();
        Task task = ts.newTask(taskId);
        task.setName("test claim task");
        ts.saveTask(task);
        //  创建用户
        String userId = UUID.randomUUID().toString();
        User user = is.newUser(userId);
        user.setFirstName("claim user");
        is.saveUser(user);

        ts.claim(taskId, userId);
        //ts.claim(taskId, "100"); //再次申领会报错的
        //ts.setAssignee(taskId, "100"); //重新指派代理人则不会报错

        List<Task> tasks = ts.createTaskQuery().taskAssignee(userId).list();
        for(Task t : tasks) {
            System.out.println(t.getName());
        }
    }
}
