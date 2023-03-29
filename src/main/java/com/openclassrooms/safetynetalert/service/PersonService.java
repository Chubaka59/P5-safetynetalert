package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.dto.childAlert.ChildAlertDTO;
import com.openclassrooms.safetynetalert.dto.childAlert.MajorPersonDTO;
import com.openclassrooms.safetynetalert.dto.childAlert.MinorPersonDTO;
import com.openclassrooms.safetynetalert.dto.communityemail.CommunityEmailDTO;
import com.openclassrooms.safetynetalert.dto.person.CreatePersonDTO;
import com.openclassrooms.safetynetalert.dto.person.UpdatePersonDTO;
import com.openclassrooms.safetynetalert.dto.personinfodto.PersonInfoDTO;
import com.openclassrooms.safetynetalert.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    /**
     * Get a list of all persons
     * @return a list of persons
     */
    List<Person> getPersons();

    /**
     * Check if the person to add exists then call the method to add it or throw an Exception
     * @param personDTO the information of the person to add
     * @return the person that has been added
     */
    Person add(CreatePersonDTO personDTO);

    /**
     * Check if the person to delete exists then call the method to delete or throw an Exception
     * @param firstName the firstName of the person to delete
     * @param lastName the lastName of the person to delete
     * @return the person that has been deleted
     */
    Person delete(String firstName, String lastName);

    /**
     * Check if the person to update exists then call the method to update or throw an Exception
     * @param personDTO the information of the person to update
     * @param firstName the firstName of the person to update
     * @param lastName the lastName of the person to update
     * @return the person that has been updated
     */
    Person update(UpdatePersonDTO personDTO, String firstName, String lastName);

    /**
     * Get a list of child filtered by address and a list of major person
     * @param address the address to filter
     * @return the ChildAlertDTO to return
     */
    ChildAlertDTO getChildListFromAddress(String address);

    /**
     * get the minor personList for ChildAlert
     * @param address the address to filter
     * @return a list of minor person
     */
    List<MinorPersonDTO> getMinorPersonDTOList(String address);

    /**
     * get the major personList for ChildAlert
     * @param address the address to filter
     * @return a list of major person
     */
    List<MajorPersonDTO> getMajorPersonDTOList(String address);

    /**
     * get the information of a specified person, if no firstName, return every person with this lastName
     * @param firstName an Optional of the firstName of the person
     * @param lastName the lastName of the person
     * @return a list of person
     */
    List<PersonInfoDTO> getPersonListFromFirstnameAndLastnameList(Optional<String> firstName, String lastName);

    /**
     * Get a list of email filtered by city
     * @param city the city to filter
     * @return a list of CommunityEmailDTO
     */
    List<CommunityEmailDTO> getEmailListFromCity(String city);
}
