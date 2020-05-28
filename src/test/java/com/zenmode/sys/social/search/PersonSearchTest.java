package com.zenmode.sys.social.search;

import com.zenmode.sys.social.TestRunner;
import com.zenmode.sys.social.entity.Person;
import com.zenmode.sys.social.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertFalse;


public class PersonSearchTest extends TestRunner {

    @Autowired
    PersonRepository personRepository;

    @Test
    public void getByLastNameCorrect() {
        PersonSpecification spec = new PersonSpecification(new SearchCriteria("lastName", "=", "Test1"));

        List<Person> results = personRepository.findAll(spec);
        System.out.println("-----------------------------------------");
        System.out.println(results.size());
        assertFalse(results.isEmpty());
    }

    @Test
    public void getMoreThanDate() {
        PersonSpecification spec = new PersonSpecification(
                new SearchCriteria("dateOfBirth", ">", LocalDate.of(1967, Month.DECEMBER, 15)));

        List<Person> results = personRepository.findAll(spec);
        System.out.println("-----------------------------------------");
        System.out.println(results.size());
        assertFalse(results.isEmpty());
    }
}
