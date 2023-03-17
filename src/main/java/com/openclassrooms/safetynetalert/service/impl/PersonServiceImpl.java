package com.openclassrooms.safetynetalert.service.impl;

import com.openclassrooms.safetynetalert.dto.childAlert.ChildAlertDTO;
import com.openclassrooms.safetynetalert.dto.childAlert.MajorPersonDTO;
import com.openclassrooms.safetynetalert.dto.childAlert.MinorPersonDTO;
import com.openclassrooms.safetynetalert.dto.person.CreatePersonDTO;
import com.openclassrooms.safetynetalert.dto.person.UpdatePersonDTO;
import com.openclassrooms.safetynetalert.exception.person.PersonAlreadyExistException;
import com.openclassrooms.safetynetalert.exception.person.PersonNotFoundException;
import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import com.openclassrooms.safetynetalert.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    @Override
    public List<Person> getPersons(){
        return personRepository.getAllPersons();
    }

    @Override
    public Person add(CreatePersonDTO personDTO) {
        if(personRepository.getPerson(personDTO.getFirstName(), personDTO.getLastName()).isPresent())
            throw new PersonAlreadyExistException(personDTO.getFirstName(), personDTO.getLastName());
        return personRepository.add(personDTO);
    }

    @Override
    public Person delete(String firstName, String lastName) {
        Person person = personRepository.getPerson(firstName, lastName)
                .orElseThrow(() -> new PersonNotFoundException(firstName, lastName));
        return personRepository.delete(person);
    }

    @Override
    public Person update(UpdatePersonDTO personDTO, String firstName, String lastName) {
        Person person = personRepository.getPerson(firstName, lastName)
                .orElseThrow(() -> new PersonNotFoundException(firstName, lastName));
        return personRepository.update(person, personDTO);
    }

    @Override
    public ChildAlertDTO getChildAlert(String address){
        List<MinorPersonDTO> minorPersonDTOList = getMinorPersonDTOList(address);

        List<MajorPersonDTO> majorPersonDTOList = getMajorPersonDTOList(address);

        return new ChildAlertDTO(minorPersonDTOList, majorPersonDTOList);
    }

    public List<MinorPersonDTO> getMinorPersonDTOList(String address) {
        return personRepository.findByAddress(address)
                .stream()
                .filter(p -> medicalRecordRepository.getMedicalRecord(p.getFirstName(), p.getLastName())
                        .orElseThrow()
                        .isMinor())
                .map(person -> new MinorPersonDTO(person, medicalRecordRepository.getMedicalRecord(person.getFirstName(), person.getLastName()).get()))
                .toList();
    }

    public List<MajorPersonDTO> getMajorPersonDTOList(String address) {
        return personRepository.findByAddress(address)
                .stream()
                .filter(p -> !medicalRecordRepository.getMedicalRecord(p.getFirstName(), p.getLastName())
                        .orElseThrow()
                        .isMinor())
                .map(MajorPersonDTO::new)
                .toList();
    }
}
