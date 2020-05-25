package com.zenmode.sys.social;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.zenmode.sys.social.Const.DATE_FORMAT;

public enum  Validator {
    ;

    public static boolean isValidDateFormat(String date) {
        try {
            new SimpleDateFormat(DATE_FORMAT).parse(date);
        } catch (ParseException ignored) {
            return false;
        }

        return true;
    }
}
