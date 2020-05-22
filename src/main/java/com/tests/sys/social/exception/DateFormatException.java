package com.tests.sys.social.exception;

import static com.tests.sys.social.Const.DATE_FORMAT;

public class DateFormatException extends RuntimeException {

    public DateFormatException(String date) {
        super("Invalid date format: " + date + ". Accepted format: " + DATE_FORMAT);
    }
}
