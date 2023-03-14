package com.openclassrooms.safetynetalert.dto;

import com.openclassrooms.safetynetalert.model.Person;
import lombok.Data;

import java.util.List;

@Data
public class ChildAlert {
    String firstName;
    String lastName;
    int age;
    List<Person> otherHouseholdMembers;
}
