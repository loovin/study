package com.tsb.study.activiti.listener.task;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class TaskListenerComplete implements TaskListener {

    public void notify(DelegateTask arg0) {
        System.out.println("完成任务的时候触发的");
    }

}
