package org.crazyit.act.aw.action;

import java.util.List;

import org.activiti.engine.TaskService;
import org.crazyit.act.aw.entity.Person;
import org.crazyit.act.aw.service.PersonService;

import com.opensymphony.xwork2.Action;

public class TestAction implements Action {

    private PersonService personService;

    private TaskService taskService;

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    public String execute() throws Exception {
        List<Person> persons = personService.listPersons();
        for (Person p : persons) {
            System.out.println(p.getId());
        }
        System.out.println("===========");
        long taskCount = taskService.createTaskQuery().count();
        System.out.println(taskCount);
        return null;
    }

}
