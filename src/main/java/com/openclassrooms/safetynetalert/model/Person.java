package com.openclassrooms.safetynetalert.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"firstName","lastName","address","city","zip","phone","email"})
public class Person {
    @JsonProperty("firstName")
    String firstName;
    @JsonProperty("lastName")
    String lastName;
    @JsonProperty("address")
    String address;
    @JsonProperty("city")
    String city;
    @JsonProperty("zip")
    String zip;
    @JsonProperty("phone")
    String phone;
    @JsonProperty("email")
    String email;
}
