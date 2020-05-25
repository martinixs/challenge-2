package com.tests.sys.social.conf;

import com.tests.sys.social.entity.Person;
import com.tests.sys.social.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Configuration
@Slf4j
public class LoadDatabase {


    @Bean
    @Transactional
    CommandLineRunner initDatabase(PersonRepository repository) {
        return args -> {
            Person p1 = new Person("Abramov",
                    "Alexey",
                    "Konstantinovich",
                    new Date());
            Person p2 = new Person(
                    "Kravcov",
                    "Maksim",
                    "Evgenivich",
                    new Date());

            Person p3 = new Person(
                    "Temnokov",
                    "Alexey",
                    "Konstantinovich",
                    new Date());

            log.info("Preloading " + repository.save(p1));
            log.info("Preloading " + repository.save(p2));
            log.info("Preloading " + repository.save(p3));

            Set<Person> relationship = new HashSet<>();
            relationship.add(p2);
            relationship.add(p3);
            p1.setRelationship(relationship);

            log.info("Update " + repository.save(p1));

        };
    }
}
