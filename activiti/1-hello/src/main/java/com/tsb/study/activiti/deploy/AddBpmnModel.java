package com.tsb.study.activiti.deploy;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;

/**
 * 通过代码直接绘制流程图
 * 1、act_re_deployment生成一条记录
 * 2、act_ge_bytearray 生成一条记录，byte 内容即为xml 定义
 */
public class AddBpmnModel {

    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 存储服务
        RepositoryService rs = engine.getRepositoryService();
        
        DeploymentBuilder builder = rs.createDeployment();
        builder.addBpmnModel("My Process 3321", createProcessModel());
        
        builder.deploy();
    }
    
    private static BpmnModel createProcessModel() {
        // 创建BPMN模型对象
        BpmnModel model = new BpmnModel();
        // 创建一个流程定义
        org.activiti.bpmn.model.Process process = new org.activiti.bpmn.model.Process();
        model.addProcess(process);
        process.setId("myProcess3321");
        process.setName("My Process 3321");
        // 开始事件
        StartEvent startEvent = new StartEvent();
        startEvent.setId("startEvent");
        process.addFlowElement(startEvent);
        // 用户任务
        UserTask userTask = new UserTask();
        userTask.setName("User Task");
        userTask.setId("userTask");
        process.addFlowElement(userTask);
        // 结束事件
        EndEvent endEvent = new EndEvent();
        endEvent.setId("endEvent");
        process.addFlowElement(endEvent);
        // 添加流程顺序
        process.addFlowElement(new SequenceFlow("startEvent", "userTask"));
        process.addFlowElement(new SequenceFlow("userTask", "endEvent"));
        return model;
    }
}
