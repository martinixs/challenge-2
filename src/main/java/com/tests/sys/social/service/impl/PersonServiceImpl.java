package com.tests.sys.social.service.impl;

import com.tests.sys.social.entity.Person;
import com.tests.sys.social.exception.PersonNotFoundException;
import com.tests.sys.social.exception.PersonValidationException;
import com.tests.sys.social.repository.PersonRepository;
import com.tests.sys.social.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person getPerson(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    @Override
    public Person createPerson(Person person) {
        try {
            return personRepository.save(person);
        } catch (Exception e) {
            throw new PersonValidationException(person);
        }
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

        person.getRelationship().clear();

        if (!person.getFriends().isEmpty()) {
            for (Person friend : person.getFriends()) {
                friend.getRelationship().remove(person);
                friend.getFriends().remove(person);
            }
            person.getFriends().clear();
        }

        personRepository.delete(person);
    }

    @Override
    public List<Person> getPersons() {
        return personRepository.findAll();
    }
}
