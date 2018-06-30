package org.crazyit.cloud.controller;

import java.util.List;

import org.crazyit.cloud.entity.Person;
import org.crazyit.cloud.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
	
	@Autowired
	private PersonService personService;

	@RequestMapping(value = "/persons", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Person> persons() {
		return personService.getPersons();
	}
	
	@RequestMapping(value = "/mq", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Person> myQuery() {
		return personService.myQuery();
	}
}
