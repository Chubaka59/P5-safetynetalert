package com.openclassrooms.safetynetalert.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalert.model.Data;
import com.openclassrooms.safetynetalert.repository.impl.DataRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class DataRepositoryTest {

    @Autowired
    DataRepositoryImpl dataRepositoryImpl;
    @Mock
    private ObjectMapper objectMapper;

    private Data data = new Data();


    @BeforeEach
    public void setUp(){
        dataRepositoryImpl = new DataRepositoryImpl(objectMapper);
    }

    @Test
    public void getDataFromFileTest() throws IOException {
        //WHEN the data are imported
        dataRepositoryImpl.getDataFromFile();

        //THEN the objectMapper is used to read value
        verify(objectMapper, times(1)).readValue(any(File.class), eq(Data.class));
    }
}
