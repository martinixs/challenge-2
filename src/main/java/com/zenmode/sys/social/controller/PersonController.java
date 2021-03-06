package com.zenmode.sys.social.controller;

import com.zenmode.sys.social.entity.Person;
import com.zenmode.sys.social.service.PersonService;
import com.zenmode.sys.social.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
public class PersonController {

    private static final String URL_PERSON_ID = "/persons/{id}";
    private static final String URL_PERSON_ID_FRIENDS = URL_PERSON_ID + "/friends";

    private final PersonService personService;
    private final SearchService searchService;

    @Autowired
    public PersonController(PersonService personService, SearchService searchService) {
        this.personService = personService;
        this.searchService = searchService;
    }

    @GetMapping("/persons")
    public List<Person> search(@RequestParam(value = "search", required = false) String search) {
        if (StringUtils.isNotEmpty(search)) {
            return searchService.findBySpec(search);
        } else {
            return personService.getPersons();
        }
    }

//    @GetMapping("/persons")
//    public List<Person> getPersons() {
//        return personService.getPersons();
//    }

    @PostMapping(path = "/persons", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person createPerson(@Valid @RequestBody Person person) {
        return personService.createPerson(person);
    }

    @GetMapping(URL_PERSON_ID)
    public Person getPerson(@PathVariable Long id) {
        return personService.getPerson(id);
    }

    @PutMapping(URL_PERSON_ID)
    public Person updatePerson(@RequestBody Person newPerson, @PathVariable Long id) {
        return personService.updatePerson(newPerson, id);
    }

    @DeleteMapping(URL_PERSON_ID)
    public void removePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }

    @GetMapping(URL_PERSON_ID_FRIENDS)
    public List<Person> getFriends(@PathVariable Long id) {
        return personService.friends(id);
    }

    @PostMapping(value = URL_PERSON_ID_FRIENDS, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> addFriend(@PathVariable Long id, @Valid @RequestBody Person person) {
        return personService.addFriend(id, person);
    }

    @PutMapping(URL_PERSON_ID_FRIENDS + "/{friendId}")
    public List<Person> addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        return personService.addFriend(id, friendId);
    }

    @DeleteMapping(URL_PERSON_ID_FRIENDS)
    public void deleteFriends(@PathVariable Long id) {
        personService.deleteAllFriends(id);
    }

    @DeleteMapping(URL_PERSON_ID_FRIENDS + "/{friendId}")
    public void deleteFriends(@PathVariable Long id, @PathVariable Long friendId) {
        personService.deleteAllFriends(id, friendId);
    }
}
