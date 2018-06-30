package org.crazyit.cloud.dao;

import org.crazyit.cloud.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonDao extends JpaRepository<Person, Integer>{//}, PersonDaoCustom {
    List<Person> myQuery();
}
