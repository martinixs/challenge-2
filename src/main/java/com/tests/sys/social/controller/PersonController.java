package com.tests.sys.social.controller;

import com.tests.sys.social.Validator;
import com.tests.sys.social.entity.Person;
import com.tests.sys.social.exception.DateFormatException;
import com.tests.sys.social.exception.PersonNotFoundException;
import com.tests.sys.social.repository.PersonRepository;
import com.tests.sys.social.repository.RelationshipRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.tests.sys.social.Const.DATE_FORMAT;

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

    @PostMapping
    public Person createPerson(@RequestBody Person person) throws DateFormatException{
        String dateOfBirth = person.getDayOfBirth();

        if(Validator.isValidDateFormat(dateOfBirth)) {
            throw new DateFormatException(dateOfBirth);
        }

        return personRepository.save(person);
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    @PutMapping("/{id}")
    public Person updatePerson(@RequestBody Person newPerson, @PathVariable Long id) {
        return personRepository.findById(id)
                .map(person -> {

                    if(StringUtils.isNotEmpty(newPerson.getFirstName())) {
                        person.setFirstName(newPerson.getFirstName());
                    }

                    if(StringUtils.isNotEmpty(newPerson.getLastName())) {
                        person.setLastName(newPerson.getLastName());
                    }

                    if(StringUtils.isNotEmpty(newPerson.getMiddleName())) {
                        person.setMiddleName(newPerson.getMiddleName());
                    }


                    if(StringUtils.isNotEmpty(newPerson.getDayOfBirth())) {
                        String dateOfBirth = newPerson.getDayOfBirth();

                        if (Validator.isValidDateFormat(dateOfBirth)) {
                            throw new DateFormatException(dateOfBirth);
                        }

                        person.setDayOfBirth(dateOfBirth);
                    }
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
