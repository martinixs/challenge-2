package com.tests.sys.social;


import com.tests.sys.social.repository.PersonRepository;
import com.tests.sys.social.repository.RelationshipRepository;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration
@ComponentScan({
        "com.tests.sys.social.controllers",
        "com.tests.sys.social.repository",
        "com.tests.sys.social.entity"})
public class TestConf {
}
