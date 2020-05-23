package com.tests.sys.social.controller.advice;


import com.tests.sys.social.exception.DateFormatException;
import com.tests.sys.social.exception.PersonNotFoundException;
import com.tests.sys.social.exception.PersonValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PersonAdvice {

    @ResponseBody
    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String personNotFoundHandler(PersonNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(DateFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String personDateOfDateHandler(DateFormatException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(PersonValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String personValidationHandler(PersonValidationException ex) {
        return ex.getMessage();
    }
}
