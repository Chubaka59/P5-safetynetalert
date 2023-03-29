package com.openclassrooms.safetynetalert.service.impl;

import com.openclassrooms.safetynetalert.dto.fire.FireDTO;
import com.openclassrooms.safetynetalert.dto.fire.FirePersonDTO;
import com.openclassrooms.safetynetalert.dto.firestation.CreateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.FireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.PersonDTO;
import com.openclassrooms.safetynetalert.dto.firestation.UpdateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.flood.FloodDTO;
import com.openclassrooms.safetynetalert.dto.flood.FloodPersonDTO;
import com.openclassrooms.safetynetalert.dto.phonealert.PhoneAlertDTO;
import com.openclassrooms.safetynetalert.exception.firestation.FireStationAlreadyExistException;
import com.openclassrooms.safetynetalert.exception.firestation.FireStationNotFoundException;
import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.repository.FireStationRepository;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import com.openclassrooms.safetynetalert.service.FireStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FireStationServiceImpl  implements FireStationService {

    private final FireStationRepository fireStationRepository;
    private final PersonRepository personRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    @Override
    public List<FireStation> getFireStations(){
        return fireStationRepository.getAllFireStations();
    }

    @Override
    public FireStation add(CreateFireStationDTO fireStationDTO) {
        if(fireStationRepository.getFireStation(fireStationDTO.getAddress()).isPresent())
            throw new FireStationAlreadyExistException(fireStationDTO.getAddress());
        return fireStationRepository.add(fireStationDTO);
    }

    @Override
    public FireStation delete(String address) {
        FireStation fireStation = fireStationRepository.getFireStation(address)
                .orElseThrow(() -> new FireStationNotFoundException(address));
        return fireStationRepository.delete(fireStation);
    }

    @Override
    public FireStation update(UpdateFireStationDTO fireStationDTO, String address) {
        FireStation fireStation = fireStationRepository.getFireStation(address)
                .orElseThrow(() -> new FireStationNotFoundException(address));
        return fireStationRepository.update(fireStation, fireStationDTO);
    }

    @Override
    public FireStationDTO getPersonsFromFireStation(int stationNumber) {
        List<String> addresses = fireStationRepository.getAddressFromStationNumber(stationNumber);
        List<PersonDTO> personDTOList = new ArrayList<>();
        for (String address:addresses) {
            personDTOList.addAll(personRepository.findByAddress(address)
                    .stream()
                    .map(PersonDTO::new)
                    .toList());
        }

        Long numberOfMinor = personDTOList
                .stream()
                .filter(p -> medicalRecordRepository.getMedicalRecord(p.getFirstName(), p.getLastName())
                        .orElseThrow()
                        .isMinor())
                .count();
        return new FireStationDTO(personDTOList, numberOfMinor);
    }

    @Override
    public List<PhoneAlertDTO> getPhoneAlertList(int firestation) {
        List<String> addresses = fireStationRepository.getAddressFromStationNumber(firestation);
        List<PhoneAlertDTO> phoneAlertDTOList = new ArrayList<>();
        for (String address : addresses) {
            phoneAlertDTOList.addAll(personRepository.findByAddress(address)
                    .stream()
                    .map(PhoneAlertDTO::new)
                    .toList());
        }
        return phoneAlertDTOList;
    }

    @Override
    public FireDTO getFireStationFromAddress(String address) {
        List<FirePersonDTO> firePersonDTOList = personRepository.getAllPersons()
                .stream()
                .filter(p -> p.getAddress().equals(address))
                .map(p -> new FirePersonDTO(p, medicalRecordRepository.getMedicalRecord(p.getFirstName(), p.getLastName()).get()))
                .toList();

        Optional<FireStation> fireStation = fireStationRepository.getFireStation(address);
        String fireStationNumber = fireStation.map(station -> String.valueOf(station.getStation())).orElse("Unknown");
        return new FireDTO(fireStationNumber, firePersonDTOList);
    }

    @Override
    public List<FloodDTO> getHomeListFromFireStationList(List<Integer> stations) {
        List<FloodDTO> floodDTOList = new ArrayList<>();
        for (int station:stations) {
            List<String> addresses = fireStationRepository.getAddressFromStationNumber(station);
            for (String address:addresses) {
                List<FloodPersonDTO> floodPersonDTOList = personRepository.getAllPersons()
                        .stream()
                        .filter(p -> p.getAddress().equals(address))
                        .map(p -> new FloodPersonDTO(p, medicalRecordRepository.getMedicalRecord(p.getFirstName(), p.getLastName()).get()))
                        .toList();

                FloodDTO floodDTO = new FloodDTO(address, floodPersonDTOList);
                floodDTOList.add(floodDTO);
            }
        }
        return floodDTOList;
    }
}
