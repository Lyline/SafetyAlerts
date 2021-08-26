package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {
  MedicalRecord getMedicalRecordById(String id);

  List<MedicalRecord> getAllMedicalRecords();

  MedicalRecord addMedicalRecord(MedicalRecord medicalRecord);

  void addAllMedicalRecords(List<MedicalRecord> list);

  MedicalRecord upDateMedicalRecord(String id, MedicalRecord medicalRecordToUpDate);

  void removeMedicalRecordById(String id);

  void removeAllMedicalRecords();
}
