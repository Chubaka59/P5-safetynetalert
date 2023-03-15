package com.openclassrooms.safetynetalert.model;

import com.openclassrooms.safetynetalert.dto.person.CreatePersonDTO;
import com.openclassrooms.safetynetalert.dto.person.UpdatePersonDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Person {
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


    public Person update(UpdatePersonDTO personDTO){
        this.setAddress(personDTO.getAddress());
        this.setCity(personDTO.getCity());
        this.setZip(personDTO.getZip());
        this.setPhone(personDTO.getPhone());
        this.setEmail(personDTO.getEmail());
        return this;
    }

    public Person create(CreatePersonDTO personDTO){
        this.setFirstName(personDTO.getFirstName());
        this.setLastName(personDTO.getLastName());
        this.setAddress(personDTO.getAddress());
        this.setCity(personDTO.getCity());
        this.setZip(personDTO.getZip());
        this.setPhone(personDTO.getPhone());
        this.setEmail(personDTO.getEmail());
        return this;
    }
}
