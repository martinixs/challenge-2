package com.zenmode.sys.social.conf;

import com.zenmode.sys.social.entity.Person;
import com.zenmode.sys.social.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;

@Configuration
@Slf4j
public class LoadDatabase {

    private static final String PERSON1 = "Test1";
    private static final String PERSON2 = "Test2";
    private static final String PERSON3 = "Test3";
    private static final String PERSON4 = "Test4";

    @Bean
    CommandLineRunner initDatabase(PersonRepository repository) {
        return args -> {
            Person p1 = new Person(PERSON1, PERSON1, PERSON1, LocalDate.of(1967, Month.DECEMBER, 15));
            Person p2 = new Person(PERSON2, PERSON2, PERSON2, LocalDate.of(1973, Month.JULY, 25));
            Person p3 = new Person(PERSON3, PERSON3, PERSON3, LocalDate.of(1989, Month.OCTOBER, 4));
            Person p4 = new Person(PERSON4, PERSON4, PERSON4, LocalDate.of(1993, Month.FEBRUARY, 1));


            log.info("Preloading " + repository.save(p1));
            log.info("Preloading " + repository.save(p2));
            log.info("Preloading " + repository.save(p3));
            log.info("Preloading " + repository.save(p4));

            p1.getFriends().add(p2);
            p1.getFriends().add(p3);
            log.info("Update " + repository.saveAndFlush(p1));

            p2.getFriends().add(p4);
            log.info("Update " + repository.saveAndFlush(p2));
        };
    }
}
