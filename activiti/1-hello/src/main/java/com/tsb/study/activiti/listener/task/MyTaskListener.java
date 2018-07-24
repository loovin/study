package com.tsb.study.activiti.listener.task;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.el.FixedValue;

public class MyTaskListener implements TaskListener {

    private FixedValue userName;
 
    public void setUserName(FixedValue userName) {
        this.userName = userName;
    }

    public void notify(DelegateTask arg0) {
        System.out.println("这是自定义任务创建时的监听器, " + userName.getExpressionText());
    }
}
