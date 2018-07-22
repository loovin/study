package com.tsb.study.activiti.api.task.variable;

import java.util.UUID;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

/**
 * 表：act_ru_variable
 */
public class TaskVariable {
    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        TaskService ts = engine.getTaskService();

        Task task = ts.newTask(UUID.randomUUID().toString());
        task.setName("variable task 1");
        ts.saveTask(task);

        ts.setVariable(task.getId(), "var1", "hello");

    }
}
