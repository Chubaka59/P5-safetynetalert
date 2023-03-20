package com.openclassrooms.safetynetalert.dto.communityemail;

import com.openclassrooms.safetynetalert.model.Person;
import lombok.Data;

@Data
public class CommunityEmailDTO {
    String mail;

    public CommunityEmailDTO(Person person){
        this.mail = person.getEmail();
    }
}
