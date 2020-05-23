package com.tests.sys.social.exception;

import com.tests.sys.social.entity.Person;

public class PersonValidationException extends RuntimeException {

    public PersonValidationException(Person person) {
        super("Invalid person: " + person);
    }
}
