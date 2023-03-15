package com.openclassrooms.safetynetalert.dto.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePersonDTO {
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
