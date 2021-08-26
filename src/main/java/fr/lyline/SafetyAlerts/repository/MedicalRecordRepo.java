package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordRepo {
  List<MedicalRecord> findAll();

  MedicalRecord findById(String firstNameLastName);

  MedicalRecord add(MedicalRecord medicalRecord);

  void addAll(List<MedicalRecord> list);

  MedicalRecord update(String id, MedicalRecord medicalRecordToUpDate);

  void deleteById(String id);

  void deleteAll();
}
