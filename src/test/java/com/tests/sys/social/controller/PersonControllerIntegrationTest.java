package com.tests.sys.social.controller;

import com.tests.sys.social.TestConf;
import com.tests.sys.social.entity.Person;
import com.tests.sys.social.utils.Const;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConf.class)
@Slf4j
class PersonControllerIntegrationTest {

    private static final String URL = "/persons";

    private static final String ID_UPDATE = "1";

    private Person person = Person.builder()
            .firstName(Const.PERSON_NAME)
            .middleName(Const.PERSON_MIDDLE_NAME)
            .lastName(Const.PERSON_SURNAME)
            .dayOfBirth(Const.PERSON_DATE_OF_BIRTH)
            .build();


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getPersons() {
        ResponseEntity<Person[]> respEntity = restTemplate.getForEntity(URL, Person[].class);

        assertEquals(HttpStatus.OK, respEntity.getStatusCode());
        assertNotNull(respEntity.getBody());
        assertTrue(respEntity.getBody().length > 0);
    }

    @Test
    public void getPersonSuccess() {
        ResponseEntity<Person> response = restTemplate.getForEntity(URL + "/" + ID_UPDATE, Person.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void getPersonFailed() {
        ResponseEntity<?> response = restTemplate.getForEntity(URL + "/100", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Can't find person with id:100", response.getBody());
    }

    @Test
    public void createNewPersonFailed() {
        Person person = Person.builder()
                .firstName(Const.PERSON_NAME)
                .middleName(Const.PERSON_MIDDLE_NAME)
                .lastName(Const.PERSON_SURNAME)
                .dayOfBirth("23-07-1990")
                .build();
        ResponseEntity<String> response = restTemplate.postForEntity(URL, person, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid date format: 23-07-1990. Accepted format: yyyy-MM-dd", response.getBody());
    }

    @Test
    public void createNewPersonSuccess() {

        ResponseEntity<Person> response = restTemplate.postForEntity(URL, person, Person.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(person.getFirstName(), response.getBody().getFirstName());
    }

    @Test
    public void updatePersonSuccess() {
        Person updateData = Person.builder().firstName(Const.PERSON_NAME).build();
        restTemplate.put(URL + "/" + ID_UPDATE, updateData, Person.class);

        ResponseEntity<Person> response = restTemplate.getForEntity(URL + "/" + ID_UPDATE, Person.class);
        assertNotNull(response.getBody());
        assertEquals(Const.PERSON_NAME, response.getBody().getFirstName());
    }

    @Test
    public void successDeletePersonWithoutRelationship() {
        restTemplate.delete(URL + "/3");

        ResponseEntity<?> response = restTemplate.getForEntity(URL + "/3", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}