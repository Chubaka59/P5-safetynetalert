package com.openclassrooms.safetynetalert.mapper;

import com.openclassrooms.safetynetalert.dto.FireStationDTO;
import com.openclassrooms.safetynetalert.dto.PersonDTO;
import com.openclassrooms.safetynetalert.repository.impl.FireStationRepositoryImpl;
import com.openclassrooms.safetynetalert.repository.impl.MedicalRecordRepositoryImpl;
import com.openclassrooms.safetynetalert.repository.impl.PersonRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FireStationDTOMapper {
    private final FireStationRepositoryImpl fireStationRepository;
    private final PersonRepositoryImpl personRepository;
    private final MedicalRecordRepositoryImpl medicalRecordRepository;


    public FireStationDTO getPersonsFromFireStation(int stationNumber) {
        List<String> addresses = fireStationRepository.getAddressFromStationNumber(stationNumber);
        List<PersonDTO> personDTOList = personRepository.getPersonsFromAddressList(addresses)
                .stream()
                .map(PersonDTO::new)
                .toList();


        Long numberOfMinor = personDTOList
                .stream()
                .filter(p -> medicalRecordRepository.getMedicalRecord(p.getFirstName(), p.getFirstName())
                            .orElseThrow()
                            .isMinor())
                .count();


        return new FireStationDTO(personDTOList, numberOfMinor );
    }


}



