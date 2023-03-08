package com.openclassrooms.safetynetalert.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Person {
    @NotEmpty
    String firstName;
    @NotEmpty
    String lastName;
    @NotEmpty
    String address;
    @NotEmpty
    String city;
    @NotEmpty
    String zip;
    @NotEmpty
    String phone;
    @NotEmpty
    String email;
}
