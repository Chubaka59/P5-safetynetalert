package com.openclassrooms.safetynetalert.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
public class Person {
    @Null(groups = OnUpdate.class)
    @NotBlank
    String firstName;
    @Null(groups = OnUpdate.class)
    @NotBlank
    String lastName;
    @NotBlank
    @NotBlank(groups = OnUpdate.class)
    String address;
    @NotBlank
    @NotBlank(groups = OnUpdate.class)
    String city;
    @NotBlank
    @NotBlank(groups = OnUpdate.class)
    String zip;
    @NotBlank
    @NotBlank(groups = OnUpdate.class)
    String phone;
    @NotBlank
    @NotBlank(groups = OnUpdate.class)
    @Email
    String email;
}
