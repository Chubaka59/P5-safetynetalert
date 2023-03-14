package com.openclassrooms.safetynetalert.dto;

import lombok.Data;

import java.util.List;

@Data
public class PersonInfoDTO {
    String firstName;
    String lastName;
    String address;
    int age;
    String mail;
    List<String> medication;
    List<String> allergies;
}
