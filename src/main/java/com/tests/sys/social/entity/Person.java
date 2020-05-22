package com.tests.sys.social.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String lastName;
    private String firstName;
    private String middleName;
    private String dayOfBirth;

//    @OneToMany
//    @JoinColumn(name = "friend_id")
//    private Set<Person> friends;
}
