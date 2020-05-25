package com.tests.sys.social.service;

import com.tests.sys.social.entity.Person;

import java.util.List;

public interface PersonService {
    Person getPerson(Long id);
    Person createPerson(Person person);
    Person updatePerson(Person newPerson, Long id);
    void deletePerson(Long id);
    List<Person> getPersons();
}
