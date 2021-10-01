package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.repository.MedicalRecordRepoImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

  MedicalRecordRepoImpl repository;

  public MedicalRecordServiceImpl(MedicalRecordRepoImpl repository) {
    this.repository = repository;
  }

  @Override
  public MedicalRecord getMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
    return repository.findByFirstNameAndLastName(firstName, lastName);
  }

  @Override
  public List<MedicalRecord> getAllMedicalRecords() {
    return repository.findAll();
  }

  @Override
  public boolean addMedicalRecord(MedicalRecord medicalRecord) {
    if (medicalRecord.getFirstName() != null
        && medicalRecord.getLastName() != null
        && medicalRecord.getBirthdate() != null) {
      boolean result = repository.add(medicalRecord);
      return result;
    } else return false;
  }

  @Override
  public boolean updateMedicalRecord(MedicalRecord medicalRecordToUpDate) {
    MedicalRecord medicalRecord = repository.findByFirstNameAndLastName(medicalRecordToUpDate.getFirstName(),
        medicalRecordToUpDate.getLastName());
    if (medicalRecord != null) {
      if (!medicalRecord.getBirthdate().equals(medicalRecordToUpDate.getBirthdate())) {
        //the birthdate must be not null on the medical record
        if (medicalRecordToUpDate.getBirthdate() != null) {
          medicalRecord.setBirthdate(medicalRecordToUpDate.getBirthdate());
        } else return false;
      }
      if (!medicalRecord.getMedications().equals(medicalRecordToUpDate.getMedications())) {
        medicalRecord.setMedications(medicalRecordToUpDate.getMedications());
      }
      if (!medicalRecord.getAllergies().equals(medicalRecordToUpDate.getAllergies())) {
        medicalRecord.setAllergies(medicalRecordToUpDate.getAllergies());
      }
      repository.update(medicalRecord);
      return true;
    } else return false;
  }

  @Override
  public boolean removeMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
    MedicalRecord medic = repository.findByFirstNameAndLastName(firstName, lastName);

    if (medic != null) {
      repository.deleteByFirstNameAndLastName(firstName, lastName);
      return true;
    }
    return false;
  }
}
