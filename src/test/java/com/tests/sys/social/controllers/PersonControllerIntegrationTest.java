package com.tests.sys.social.controllers;

import com.tests.sys.social.TestConf;
import com.tests.sys.social.entity.Person;
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


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConf.class)
@Slf4j
class PersonControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void getPersons() {
        ResponseEntity<Person[]> respEntity = restTemplate.getForEntity("/persons", Person[].class);

        assertEquals(HttpStatus.OK, respEntity.getStatusCode());
        assertNotNull(respEntity.getBody());
        assertEquals(2, respEntity.getBody().length);
    }

    @Test
    void getPersonSuccess() {
        ResponseEntity<Person> response = restTemplate.getForEntity("/persons/1", Person.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getPersonFailed() {
        ResponseEntity<?> response = restTemplate.getForEntity("/persons/5", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Can't find person with id:5", response.getBody());
    }
}