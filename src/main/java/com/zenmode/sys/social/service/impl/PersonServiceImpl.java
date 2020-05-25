package com.zenmode.sys.social.service.impl;

import com.zenmode.sys.social.entity.Person;
import com.zenmode.sys.social.exception.PersonNotFoundException;
import com.zenmode.sys.social.repository.PersonRepository;
import com.zenmode.sys.social.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Person getPerson(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    @Override
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person updatePerson(Person newPerson, Long id) {
        return personRepository.findById(id)
                .map(person -> {

                    person.setFirstName(newPerson.getFirstName());
                    person.setLastName(newPerson.getLastName());
                    person.setMiddleName(newPerson.getMiddleName());
                    person.setDateOfBirth(newPerson.getDateOfBirth());

                    return personRepository.save(person);
                }).orElseGet(() -> {
                    newPerson.setId(id);
                    return personRepository.save(newPerson);
                });
    }


    @Override
    public void deletePerson(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

        person.getFriends().clear();

        if (!person.getFollowers().isEmpty()) {
            for (Person friend : person.getFollowers()) {
                friend.getFriends().remove(person);
                friend.getFollowers().remove(person);
            }
            person.getFollowers().clear();
        }

        personRepository.delete(person);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    public List<Person> friends(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

        Set<Person> friends = person.getFriends();

        return new ArrayList<>(friends);
    }
}
