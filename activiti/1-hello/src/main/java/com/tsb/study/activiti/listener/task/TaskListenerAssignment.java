package com.tsb.study.activiti.listener.task;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class TaskListenerAssignment implements TaskListener {

    public void notify(DelegateTask arg0) {
        System.out.println("指定代理人时触发的");
    }

}
