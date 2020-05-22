package com.tests.sys.social.controllers;

import com.tests.sys.social.entity.Person;
import com.tests.sys.social.exception.PersonNotFoundException;
import com.tests.sys.social.repository.PersonRepository;
import com.tests.sys.social.repository.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("persons")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;


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
    public Person createPerson(@RequestBody Person person) {
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
                    person.setFirstName(newPerson.getFirstName());
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
