package com.zenmode.sys.social;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
@ComponentScan({
        "com.tests.sys.social.controllers",
        "com.tests.sys.social.repository",
        "com.tests.sys.social.entity",
        "com.tests.sys.social.search"})
public class TestConf {
}
