package com.zenmode.sys.social.controller;

import com.zenmode.sys.social.TestConf;
import com.zenmode.sys.social.entity.Person;
import com.zenmode.sys.social.utils.Const;
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

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;

import static com.zenmode.sys.social.utils.Const.PERSON_MIDDLE_NAME;
import static com.zenmode.sys.social.utils.Const.PERSON_NAME;
import static com.zenmode.sys.social.utils.Const.PERSON_SURNAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConf.class)
@Slf4j
class PersonControllerIntegrationTest {

    private static final String URL = "/persons";
    private static final String URL_PERSON_1 = URL + "/1";
    private static final String URL_PERSON_1_FRIENDS = URL_PERSON_1 + "/friends";

    private final Person person = new Person(PERSON_SURNAME, PERSON_NAME, PERSON_MIDDLE_NAME, LocalDate.of(1967, Month.DECEMBER, 15));


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
        ResponseEntity<Person> response = restTemplate.getForEntity(URL_PERSON_1, Person.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void getPersonFailed() {
        ResponseEntity<?> response = restTemplate.getForEntity(URL + "/100", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Can't find person with id: 100", response.getBody());
    }

    @Test
    public void createNewPersonFailed() {
        Person p = new Person(null,
                PERSON_NAME,
                PERSON_MIDDLE_NAME,
                LocalDate.of(1967, Month.DECEMBER, 15));
        ResponseEntity<String> response = restTemplate.postForEntity(URL, p, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("Last Name can't be null"));
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

        restTemplate.put(URL_PERSON_1, person, Person.class);

        ResponseEntity<Person> response = restTemplate.getForEntity(URL_PERSON_1, Person.class);
        assertNotNull(response.getBody());
        assertEquals(Const.PERSON_NAME, response.getBody().getFirstName());
    }

    @Test
    @Transactional
    public void successDeletePersonWithRelationship() {
        restTemplate.delete(URL + "/3");

        ResponseEntity<?> response = restTemplate.getForEntity(URL + "/3", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getFriends() {
        ResponseEntity<Person[]> respEntity = restTemplate.getForEntity(URL_PERSON_1_FRIENDS, Person[].class);
        assertEquals(HttpStatus.OK, respEntity.getStatusCode());
        assertNotNull(respEntity.getBody());
        assertTrue(respEntity.getBody().length > 0);
    }

    @Test
    public void addNewFriend() {
        ResponseEntity<Person[]> respEntity = restTemplate.getForEntity(URL_PERSON_1_FRIENDS, Person[].class);
        assertEquals(HttpStatus.OK, respEntity.getStatusCode());
        assertNotNull(respEntity.getBody());
        int countOfFriend = respEntity.getBody().length;

        ResponseEntity<Person[]> response = restTemplate.postForEntity(URL_PERSON_1_FRIENDS, person, Person[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals(countOfFriend + 1, response.getBody().length);

    }

    @Test
    public void addNewFriendById() {
        ResponseEntity<Person[]> respEntity = restTemplate.getForEntity(URL_PERSON_1_FRIENDS, Person[].class);
        assertEquals(HttpStatus.OK, respEntity.getStatusCode());
        assertNotNull(respEntity.getBody());
        int countOfFriend = respEntity.getBody().length;

        restTemplate.put(URL_PERSON_1_FRIENDS +"/4", Person[].class);

        ResponseEntity<Person[]> responseEntityAfter = restTemplate.getForEntity(URL_PERSON_1_FRIENDS, Person[].class);
        assertEquals(HttpStatus.OK, responseEntityAfter.getStatusCode());
        assertNotNull(responseEntityAfter.getBody());

        assertEquals(countOfFriend + 1, responseEntityAfter.getBody().length);
    }

    @Test
    public void removeFriendById() {
        ResponseEntity<Person[]> respEntity = restTemplate.getForEntity(URL_PERSON_1_FRIENDS, Person[].class);
        assertEquals(HttpStatus.OK, respEntity.getStatusCode());
        assertNotNull(respEntity.getBody());
        int countOfFriend = respEntity.getBody().length;

        restTemplate.delete(URL_PERSON_1_FRIENDS + "/2");

        ResponseEntity<Person[]> responseEntityAfter = restTemplate.getForEntity(URL_PERSON_1_FRIENDS, Person[].class);
        assertEquals(HttpStatus.OK, responseEntityAfter.getStatusCode());
        assertNotNull(responseEntityAfter.getBody());

        assertEquals(countOfFriend - 1, responseEntityAfter.getBody().length);
    }

    @Test
    public void removeAllFriends() {
        restTemplate.delete(URL_PERSON_1_FRIENDS);

        ResponseEntity<Person[]> responseEntityAfter = restTemplate.getForEntity(URL_PERSON_1_FRIENDS, Person[].class);
        assertEquals(HttpStatus.OK, responseEntityAfter.getStatusCode());
        assertNotNull(responseEntityAfter.getBody());

        assertEquals(0, responseEntityAfter.getBody().length);
    }
}