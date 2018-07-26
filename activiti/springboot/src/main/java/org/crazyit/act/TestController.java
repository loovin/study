package org.crazyit.act;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }
    
    @Autowired
    private RuntimeService runtimeService;
    
    @Autowired
    private TaskService taskService;
    
    @RequestMapping("/start")
    @ResponseBody
    public String start() {
        String piId = runtimeService.startProcessInstanceByKey("actProcess").getId();
        Task task = taskService.createTaskQuery().processInstanceId(piId).singleResult();
        System.out.println("当前流程节点：" + task.getName());
        
        return task.getId();
    }
    
    @RequestMapping("/complete/{taskId}")
    @ResponseBody
    public String complete(@PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String piId = task.getProcessInstanceId();
        
        taskService.complete(task.getId());
        
        // 查询当前流程节点
        Task currentTask = taskService.createTaskQuery().processInstanceId(piId).singleResult();
        System.out.println("当前流程节点：" + currentTask.getName());
        
        return "success";
    }
}
