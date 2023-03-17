package com.openclassrooms.safetynetalert.dto.phonealert;

import com.openclassrooms.safetynetalert.model.Person;
import lombok.Data;

@Data
public class PhoneAlertDTO {
    String phone;

    public PhoneAlertDTO(Person person){
        phone = person.getPhone();
    }
}
