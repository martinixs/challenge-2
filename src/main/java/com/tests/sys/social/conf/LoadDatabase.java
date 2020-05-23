package com.tests.sys.social.conf;

import com.tests.sys.social.entity.Person;
import com.tests.sys.social.entity.RelationType;
import com.tests.sys.social.entity.Relationship;
import com.tests.sys.social.repository.PersonRepository;
import com.tests.sys.social.repository.RelationshipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;
import java.util.Date;

@Configuration
@Slf4j
@Transactional
public class LoadDatabase {


    @Bean
    CommandLineRunner initDatabase(PersonRepository repository, RelationshipRepository relationship) {
        return args -> {
            Person p1 = new Person(
                    1L,
                    "Abramov",
                    "Alexey",
                    "Konstantinovich",
                    new Date());
            Person p2 = new Person(
                    2L,
                    "Kravcov",
                    "Maksim",
                    "Evgenivich",
                    new Date());
            log.info("Preloading " + repository.save(p1));
            log.info("Preloading " + repository.save(p2));

            Relationship relation12 = new Relationship(1L, p1, p2, RelationType.FRIEND);
            log.info("Update friendship:  " + relationship.save(relation12));

            Relationship relationship21 = new Relationship(2L, p2, p1, RelationType.FRIEND);
            log.info("Update friendship:  " + relationship.save(relationship21));

            Person p3 = new Person(
                    1L,
                    "Temnokov",
                    "Alexey",
                    "Konstantinovich",
                    new Date());
            log.info("Preloading " + repository.save(p3));
        };
    }
}
