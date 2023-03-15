package com.openclassrooms.safetynetalert.repository.impl;

import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.repository.DataRepository;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {
    private final DataRepository dataRepository;

    @Override
    public List<MedicalRecord> getAllMedicalRecords(){
        return dataRepository.getMedicalRecords();
    }

    @Override
    public boolean add(MedicalRecord medicalRecord) {
        if (!isDuplicated(medicalRecord))
            return dataRepository.getMedicalRecords().add(medicalRecord);
        return false;
    }

    @Override
    public boolean delete(String firstName, String lastName) {
        return dataRepository.getMedicalRecords().removeIf(m -> m.getFirstName().equals(firstName) && m.getLastName().equals(lastName));
    }

    @Override
    public boolean update(MedicalRecord medicalRecord, String firstName, String lastName) {
        AtomicBoolean isDone = new AtomicBoolean(false);
        dataRepository.getMedicalRecords()
                .stream()
                .filter(i -> i.getLastName().equals(lastName) && i.getFirstName().equals(firstName))
                .findFirst()
                .ifPresent(m -> {
                    m.setBirthdate(medicalRecord.getBirthdate());
                    m.setMedications(medicalRecord.getMedications());
                    m.setAllergies(medicalRecord.getAllergies());
                    isDone.set(true);
                });
        return isDone.get();
    }

    @Override
    public boolean isDuplicated(MedicalRecord medicalRecord) {
        return dataRepository.getMedicalRecords()
                .stream().anyMatch(m -> m.getFirstName().equals(medicalRecord.getFirstName()) &&
                        m.getLastName().equals(medicalRecord.getLastName()) &&
                        m.getBirthdate().equals(medicalRecord.getBirthdate()) &&
                        m.getAllergies().equals(medicalRecord.getAllergies()) &&
                        m.getMedications().equals(medicalRecord.getMedications()));
    }

    @Override
    public LocalDate getBirthdateListFromPersonList(Person person) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String birthdate = dataRepository.getMedicalRecords()
                    .stream()
                    .filter(p -> p.getFirstName().equals(person.getFirstName()) &&
                            p.getLastName().equals(person.getLastName()))
                    .findFirst()
                    .map(m -> m.getBirthdate())
                    .get()
                    .toString();
        return LocalDate.parse(birthdate, format);
    }

    @Override
    public int getAge(LocalDate birthdate) {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }
}
