package com.zenmode.sys.social.exception;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(Long id) {
        super("Can't find person with id: " + id);
    }
}
