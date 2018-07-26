package com.tsb.study.activiti.ruletest;

import java.util.Collection;

//import org.kie.api.io.ResourceType;
//import org.kie.internal.KnowledgeBase;
//import org.kie.internal.builder.KnowledgeBuilder;
//import org.kie.internal.builder.KnowledgeBuilderFactory;
//import org.kie.internal.definition.KnowledgePackage;
//import org.kie.internal.io.ResourceFactory;
//import org.kie.internal.runtime.StatefulKnowledgeSession;

public class DiscountTest {

    public static void main(String[] args) {
        /*// 创建一个KnowledgeBuilder
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        // 添加规则资源到 KnowledgeBuilder
        kbuilder.add(ResourceFactory.newClassPathResource("ruletest/sale.drl",
                DiscountTest.class), ResourceType.DRL);
        // 获取知识包集合
        Collection<KnowledgePackage> pkgs = kbuilder
                .getKnowledgePackages();
        // 创建KnowledgeBase实例
        KnowledgeBase kbase = kbuilder.newKnowledgeBase(); 
        // 将知识包部署到KnowledgeBase中
        kbase.addKnowledgePackages(pkgs);
        // 使用KnowledgeBase创建StatefulKnowledgeSession
        StatefulKnowledgeSession ksession = kbase
                .newStatefulKnowledgeSession();
        
        
        // 定义一个事实对象
        Member m = new Member();
        m.setIdentity("silver");
        m.setAmount(102);
        // 向StatefulKnowledgeSession中加入事实
        ksession.insert(m);
        // 匹配规则
        ksession.fireAllRules();
        
        
        System.out.println("优惠后金额：" + m.getResult());
        
        // 关闭当前session的资源
        ksession.dispose();*/
    }

}
