package com.openclassrooms.safetynetalert.repository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
public class PersonRepositoryTest {

    @Mock
    private DataRepository dataRepository;

    @MockBean
    private PersonRepository personRepository;
}
