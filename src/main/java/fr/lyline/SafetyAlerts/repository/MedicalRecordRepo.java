package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordRepo {
  List<MedicalRecord> findAll();

  MedicalRecord findById(String firstNameLastName);

  void add(MedicalRecord medicalRecord);

  void addAll(List<MedicalRecord> list);

  void update(String id, MedicalRecord medicalRecordToUpDate);

  void deleteById(String id);

  void deleteAll();
}
