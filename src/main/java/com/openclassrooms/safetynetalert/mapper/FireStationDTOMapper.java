package com.openclassrooms.safetynetalert.mapper;

import com.openclassrooms.safetynetalert.dto.FireStationDTO;
import com.openclassrooms.safetynetalert.dto.PersonDTO;
import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.repository.impl.FireStationRepositoryImpl;
import com.openclassrooms.safetynetalert.repository.impl.MedicalRecordRepositoryImpl;
import com.openclassrooms.safetynetalert.repository.impl.PersonRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FireStationDTOMapper {
    private final FireStationRepositoryImpl fireStationRepository;
    private final PersonRepositoryImpl personRepository;
    private final MedicalRecordRepositoryImpl medicalRecordRepository;


    public FireStationDTO getPersonsFromFireStation(int stationNumber) {
        List<PersonDTO> personDTOList = new ArrayList<>();
        PersonDTO personDTO = new PersonDTO();
        FireStationDTO fireStationDTO = new FireStationDTO();
        int major = 0;
        int minor = 0;
        List<String> addresses = fireStationRepository.getAddressFromStationNumber(stationNumber);
        List<Person> personListFoundFromAddress = personRepository.getPersonsFromAddressList(addresses);

        for (Person person:personListFoundFromAddress) {
            personDTO.setFirstName(person.getFirstName());
            personDTO.setLastName(person.getLastName());
            personDTO.setAddress(person.getAddress() + " " + person.getZip() + " " + person.getCity());
            personDTO.setPhone(person.getPhone());
            personDTOList.add(personDTO);

            LocalDate birthdate = medicalRecordRepository.getBirthdateListFromPersonList(person);
            if (medicalRecordRepository.getAge(birthdate) >= 18) {
                major += 1;
            } else {
                minor += 1;
            }
        }
        fireStationDTO.setPersonDTOList(personDTOList);
        fireStationDTO.setNumberOfMajor(major);
        fireStationDTO.setNumberOfMinor(minor);
        return fireStationDTO;
    }


}



