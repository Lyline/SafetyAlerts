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
  public MedicalRecord getMedicalRecordById(String id) {
    return repository.findById(id);
  }

  @Override
  public List<MedicalRecord> getAllMedicalRecords() {
    return repository.findAll();
  }

  @Override
  public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
    if (medicalRecord.getFirstName() != null
        && medicalRecord.getLastName() != null
        && medicalRecord.getBirthdate() != null) {
      return repository.add(medicalRecord);
    } else return null;
  }

  @Override
  public void addAllMedicalRecords(List<MedicalRecord> list) {
    for (MedicalRecord medicalRecord : list) addMedicalRecord(medicalRecord);
  }

  @Override
  public MedicalRecord upDateMedicalRecord(String id, MedicalRecord medicalRecordToUpDate) {
    MedicalRecord medicalRecord = repository.findById(id);
    if (medicalRecord != null) {
      if (medicalRecordToUpDate.getBirthdate() != null) {
        medicalRecord.setBirthdate(medicalRecordToUpDate.getBirthdate());
      }
      if (medicalRecordToUpDate.getMedications() != null) {
        medicalRecord.setMedications(medicalRecordToUpDate.getMedications());
      }
      if (medicalRecordToUpDate.getAllergies() != null) {
        medicalRecord.setAllergies(medicalRecordToUpDate.getAllergies());
      }
    }
    return repository.update(id, medicalRecord);
  }

  @Override
  public void removeMedicalRecordById(String id) {
    repository.deleteById(id);
  }

  @Override
  public void removeAllMedicalRecords() {
    repository.deleteAll();
  }
}
