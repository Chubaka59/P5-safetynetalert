package com.openclassrooms.safetynetalert.dto.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePersonDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String address;
    @NotBlank
    private String city;
    @NotBlank
    private String zip;
    @NotBlank
    private String phone;
    @NotBlank
    @Email
    private String email;
}
