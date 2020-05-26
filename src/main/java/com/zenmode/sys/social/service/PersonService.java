package com.zenmode.sys.social.service;

import com.zenmode.sys.social.entity.Person;

import java.util.List;

public interface PersonService {
    Person getPerson(Long id);
    Person createPerson(Person person);
    Person updatePerson(Person newPerson, Long id);
    void deletePerson(Long id);
    List<Person> getPersons();

    List<Person> friends(Long id);
    List<Person> addFriend(Long id, Person person);
    List<Person> addFriend(Long id, Long friendId);
    void deleteFriend(Long id, Long friendId);
    void deleteFriend(Long id);
}
