package com.openclassrooms.safetynetalert.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
public class Person {
    @Null(groups = OnUpdate.class)
    @NotBlank
    private String firstName;
    @Null(groups = OnUpdate.class)
    @NotBlank
    private String lastName;
    @NotBlank
    @NotBlank(groups = OnUpdate.class)
    private String address;
    @NotBlank
    @NotBlank(groups = OnUpdate.class)
    private String city;
    @NotBlank
    @NotBlank(groups = OnUpdate.class)
    private String zip;
    @NotBlank
    @NotBlank(groups = OnUpdate.class)
    private String phone;
    @NotBlank
    @NotBlank(groups = OnUpdate.class)
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
