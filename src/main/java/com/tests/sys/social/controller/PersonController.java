package com.tests.sys.social.controller;

import com.tests.sys.social.entity.Person;
import com.tests.sys.social.exception.PersonNotFoundException;
import com.tests.sys.social.exception.PersonValidationException;
import com.tests.sys.social.repository.PersonRepository;
import com.tests.sys.social.repository.RelationshipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("persons")
public class PersonController {

    private PersonRepository personRepository;
    private RelationshipRepository relationshipRepository;

    @Autowired
    public PersonController(PersonRepository personRepository, RelationshipRepository relationshipRepository) {
        this.personRepository = personRepository;
        this.relationshipRepository = relationshipRepository;
    }

    @GetMapping
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    @GetMapping("/{id}/friends")
    public List<Person> getFriends(@PathVariable Long id) {
        return relationshipRepository.findFriendsById(id);
    }

    @GetMapping("/{id}/followers")
    public List<Person> getFollowers(@PathVariable Long id) {
        return relationshipRepository.findFollowersById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Person createPerson(@RequestBody Person person) throws PersonValidationException {
        log.debug("Request:" + person);
        try {
            person = personRepository.save(person);
        } catch (Exception e) {
            throw new PersonValidationException(person);
        }
//        String dateOfBirth = person.getDayOfBirth();
//
//        if (Validator.isValidDateFormat(dateOfBirth)) {
//            throw new DateFormatException(dateOfBirth);
//        }
        return person;

    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    @PutMapping("/{id}")
    public Person updatePerson(@RequestBody Person newPerson, @PathVariable Long id) {
        return personRepository.findById(id)
                .map(person -> {

                    person.setFirstName(newPerson.getFirstName());
                    person.setLastName(newPerson.getLastName());
                    person.setMiddleName(newPerson.getMiddleName());
//                    String dateOfBirth = newPerson.getDayOfBirth();
//
//                    if (Validator.isValidDateFormat(dateOfBirth)) {
//                        throw new DateFormatException(dateOfBirth);
//                    }

                    person.setDateOfBirth(newPerson.getDateOfBirth());

                    return personRepository.save(person);
                }).orElseGet(() -> {
                    newPerson.setId(id);
                    return personRepository.save(newPerson);
                });
    }

    @DeleteMapping("/{id}")
    public void removePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
    }
}
