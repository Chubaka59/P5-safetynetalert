package com.openclassrooms.safetynetalert.repository;

import org.springframework.boot.test.mock.mockito.MockBean;


public class PersonRepositoryTest {
    private DataRepository dataRepository;

    @MockBean
    private PersonRepository personRepository;
}
