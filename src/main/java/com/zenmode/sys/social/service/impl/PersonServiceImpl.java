package com.zenmode.sys.social.service.impl;

import com.zenmode.sys.social.entity.Person;
import com.zenmode.sys.social.exception.PersonNotFoundException;
import com.zenmode.sys.social.repository.PersonRepository;
import com.zenmode.sys.social.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

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
        removeAllConnection(person);
        personRepository.delete(person);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> friends(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        return getMutualConnection(person);
    }

    @Override
    public List<Person> addFriend(Long id, Person person) {
        Person friend = personRepository.save(person);
        Person user = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

        return addFriend(user, friend);
    }

    @Override
    public List<Person> addFriend(Long id, Long friendId) {
        Person friend = personRepository.findById(friendId).orElseThrow(() -> new PersonNotFoundException(friendId));
        Person user = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

        return addFriend(user, friend);
    }

    @Override
    public void deleteAllFriends(Long id, Long friendId) {
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        Person friend = personRepository.findById(friendId).orElseThrow(() -> new PersonNotFoundException(friendId));

        if (isFriend(person, friendId)) {
            person.getFriends().remove(friend);
            personRepository.save(person);

            friend.getFriends().remove(person);
            personRepository.save(friend);
        }
    }

    @Override
    public void deleteAllFriends(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

        removeAllConnection(person);
        person.getFriends().clear();

        personRepository.save(person);
    }

    private List<Person> addFriend(Person user, Person friend) {

        user.getFriends().add(friend);
        friend.getFollowers().add(user);
        personRepository.save(user);

        friend.getFriends().add(user);
        user.getFollowers().add(friend);
        personRepository.save(friend);

        return getMutualConnection(user);
    }

    private List<Person> getMutualConnection(Person person) {
        Set<Person> friends = new HashSet<>(person.getFriends());
        friends.retainAll(person.getFollowers());
        return new ArrayList<>(friends);
    }

    private boolean isFriend(Person person, Long friendId) {
        return getMutualConnection(person).stream().anyMatch(p -> p.getId().equals(friendId));
    }

    private void removeAllConnection(Person person) {
        person.getFollowers().forEach(p -> p.getFriends().remove(person));
    }
}
