package com.zenmode.sys.social.controller;

import com.zenmode.sys.social.entity.Person;
import com.zenmode.sys.social.service.PersonService;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("persons")
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getPersons() {
        return personService.getPersons();
    }

    @GetMapping("/{id}/friends")
    public List<Person> getFriends(@PathVariable Long id) {
        return personService.friends(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Person createPerson(@Valid @RequestBody Person person) {
        return personService.createPerson(person);
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personService.getPerson(id);
    }

    @PutMapping("/{id}")
    public Person updatePerson(@RequestBody Person newPerson, @PathVariable Long id) {
        return personService.updatePerson(newPerson, id);
    }

    @DeleteMapping("/{id}")
    public void removePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }
}
