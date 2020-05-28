package com.zenmode.sys.social.service;

import com.zenmode.sys.social.entity.Person;

import java.util.List;

public interface SearchService {

    List<Person> findBySpec(String search);
}
