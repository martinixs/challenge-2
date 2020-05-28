package com.zenmode.sys.social.service.impl;

import com.zenmode.sys.social.entity.Person;
import com.zenmode.sys.social.repository.PersonRepository;
import com.zenmode.sys.social.search.PersonSpecificationBuilder;
import com.zenmode.sys.social.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SearchServiceImpl implements SearchService {

    private final PersonRepository personRepository;

    @Autowired
    public SearchServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> findBySpec(String search) {
        PersonSpecificationBuilder builder = new PersonSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(=|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Person> spec = builder.build();
        return personRepository.findAll(spec);
    }
}
