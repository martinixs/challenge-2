package com.zenmode.sys.social.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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


@Entity
@JsonIgnoreProperties({"followers", "friends"})
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PERSON_ID")
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotNull(message = "Last Name can't be null")
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Getter
    @Setter
    @NotNull(message = "First Name can't be null")
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Getter
    @Setter
    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Getter
    @Setter
    @NotNull(message = "Date of birth can't be null")
    @Column(name = "DATE_OF_BIRTH", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;


    @Getter
    @Setter
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "RELATIONSHIP",
            joinColumns = {@JoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID")},
            inverseJoinColumns = {@JoinColumn(name = "FRIEND_ID", referencedColumnName = "PERSON_ID")})
    private Set<Person> friends = new HashSet<>();

    @Getter
    @Setter
    @ManyToMany(mappedBy = "friends")
    private Set<Person> followers = new HashSet<>();


    public Person() {
    }

    public Person(String lastName, String firstName, String middleName, LocalDate dateOfBirth) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(middleName, person.middleName) &&
                Objects.equals(dateOfBirth, person.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, middleName, dateOfBirth);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
