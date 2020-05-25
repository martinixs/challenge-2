package com.tests.sys.social;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
@ComponentScan({
        "com.tests.sys.social.controllers",
        "com.tests.sys.social.repository",
        "com.tests.sys.social.entity"})
public class TestConf {
}
