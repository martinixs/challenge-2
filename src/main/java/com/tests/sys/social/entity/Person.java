package com.tests.sys.social.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Идентификатор
 * <p>
 * <p>
 * Фамилия/строка/До 255 символов
 * Имя/строка/До 255 символов
 * Отчество/строка/До 255 символов
 * Дата рождения/строка/Формат yyyy-MM-dd
 * Список друзей/ссылка
 */

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String firstName;
    private String middleName;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    //private Set<Person> friends;
}
