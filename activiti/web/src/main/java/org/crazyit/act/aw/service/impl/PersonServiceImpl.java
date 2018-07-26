package org.crazyit.act.aw.service.impl;

import java.util.List;

import org.crazyit.act.aw.dao.PersonDao;
import org.crazyit.act.aw.entity.Person;
import org.crazyit.act.aw.service.PersonService;

public class PersonServiceImpl implements PersonService {
    
    private PersonDao personDao;
    
    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }

    public List<Person> listPersons() {
        return personDao.list();
    }

    
}
