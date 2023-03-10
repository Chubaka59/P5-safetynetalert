package com.openclassrooms.safetynetalert.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordCRUDIT {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void getTest() throws Exception {
        //WHEN we perform a get THEN the status is OK and we can find "John" in the medicalRecordList
        mockMvc.perform(get("/medicalrecords"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("John")));
    }

    @Test
    public void addTest() throws Exception {
        //GIVEN we need to add a medicalRecord
        MockHttpServletRequestBuilder requestBuilders = post("/medicalrecords")
                .content("""
                            {
                                "firstName": "Joffrey",
                                "lastName": "Lefebvre",
                                "birthdate":"03/06/1984",
                                "medications":[],
                                "allergies":[]
                            }\
                        """)
                .contentType(MediaType.APPLICATION_JSON);

        //WHEN we post a new person THEN the status is CREATED
        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void addWhenAFieldIsMissingTest() throws Exception {
        //GIVEN we need to add a medicalRecord where a field is missing
        MockHttpServletRequestBuilder requestBuilders = post("/medicalrecords")
                .content("""
                            {
                                "firstName": "Joffrey",
                                "lastName": "Lefebvre",
                                "birthdate":"03/06/1984",
                                "medications":[],
                            }\
                        """)
                .contentType(MediaType.APPLICATION_JSON);

        //WHEN we add the medicalRecord THEN the status is BAD_REQUEST
        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addWhenMedicalRecordAlreadyExistTest() throws Exception {
        //GIVEN we need to add a medicalRecord that already exist
        MockHttpServletRequestBuilder requestBuilders = post("/medicalrecords")
                .content("""
                            {
                                "firstName": "John",
                                "lastName": "Boyd",
                                "birthdate":"03/06/1984",
                                "medications":[
                                    "aznol:350mg",
                                    "hydrapermazol:100mg"
                                ],
                                "allergies":[
                                    "nillacilan"
                                    ]
                            }\
                        """)
                .contentType(MediaType.APPLICATION_JSON);

        //WHEN we add the medicalRecord THEN the status is CONFLICT
        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void deleteTest() throws Exception {
        //WHEN we delete a medicalRecord THEN the status is OK
        mockMvc.perform(delete("/medicalrecords/John&Boyd"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNotExistingMedicalRecordTest() throws Exception {
        //WHEN we delete a medicalRecord that doesn't exist THEN the status is NOT_FOUND
        mockMvc.perform(delete("/medicalrecords/test&test"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void UpdateTest() throws Exception {
        //GIVEN we need to update a medicalRecord
        MockHttpServletRequestBuilder requestBuilders = put("/medicalrecords/John&Boyd")
                .content("""
                            {
                                "birthdate":"03/06/1984",
                                "medications":[
                                    "aznol:350mg",
                                    "hydrapermazol:100mg"
                                ],
                                "allergies":[
                                    "nillacilan"
                                    ]
                            }\
                        """)
                .contentType(MediaType.APPLICATION_JSON);

        //WHEN the update request is sent THEN the status is OK
        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateNotExistingMedicalRecordTest() throws Exception {
        //GIVEN we will update a medicalRecord that doesn't exist
        MockHttpServletRequestBuilder requestBuilders = put("/medicalrecords/test&test")
                .content("""
                            {
                                "birthdate":"03/06/1984",
                                "medications":[
                                    "aznol:350mg",
                                    "hydrapermazol:100mg"
                                ],
                                "allergies":[
                                    "nillacilan"
                                    ]
                            }\
                        """)
                .contentType(MediaType.APPLICATION_JSON);

        //WHEN the update request is sent THEN the status is NOT_FOUND
        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateWhenAFieldIsMissingTest() throws Exception {
        //GIVEN we will try to update a person where a field is missing
        MockHttpServletRequestBuilder requestBuilders = put("/medicalrecords/John&Boyd")
                .content("""
                            {
                                "medications":[
                                    "aznol:350mg",
                                    "hydrapermazol:100mg"
                                ],
                                "allergies":[
                                    "nillacilan"
                                    ]
                            }\
                        """)
                .contentType(MediaType.APPLICATION_JSON);

        //WHEN the update request is sent THEN the status is BAD_REQUEST
        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}

