package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {
  MedicalRecord getMedicalRecordByFirstNameAndLastName(String firstName, String lastName);

  List<MedicalRecord> getAllMedicalRecords();

  boolean addMedicalRecord(MedicalRecord medicalRecord);

  boolean updateMedicalRecord(MedicalRecord medicalRecordToUpDate);

  boolean removeMedicalRecordByFirstNameAndLastName(String firstName, String lastName);
}
