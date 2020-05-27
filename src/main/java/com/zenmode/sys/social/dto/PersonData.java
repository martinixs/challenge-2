package com.zenmode.sys.social.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PersonData {
    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dayOfBirth;
}
