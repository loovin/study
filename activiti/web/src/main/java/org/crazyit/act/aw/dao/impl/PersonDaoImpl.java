package org.crazyit.act.aw.dao.impl;

import java.util.List;

import org.crazyit.act.aw.dao.PersonDao;
import org.crazyit.act.aw.entity.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class PersonDaoImpl implements PersonDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Person> list() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Person").list();
    }

}
