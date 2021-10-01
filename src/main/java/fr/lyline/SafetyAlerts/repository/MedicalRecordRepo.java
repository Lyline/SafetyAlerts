package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordRepo {
  List<MedicalRecord> findAll();

  MedicalRecord findByFirstNameAndLastName(String firstName, String lastName);

  boolean add(MedicalRecord medicalRecord);

  boolean update(MedicalRecord medicalRecordToUpDate);

  boolean deleteByFirstNameAndLastName(String firstName, String lastName);
}
