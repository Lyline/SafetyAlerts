package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.MedicalRecord;

import java.util.List;

/**
 The interface Medical record service.

 @author Quesne GC
 @see fr.lyline.SafetyAlerts.repository.MedicalRecordRepo
 @since 0.1 */
public interface MedicalRecordService {
  /**
   Gets medical record by first name and last name of a person.

   @param firstName the first name of this person
   @param lastName  the last name of this person

   @return the medical record

   @see fr.lyline.SafetyAlerts.repository.MedicalRecordRepo
   */
  MedicalRecord getMedicalRecordByFirstNameAndLastName(String firstName, String lastName);

  /**
   Gets all medical records.

   @return the list of all medical records
   */
  List<MedicalRecord> getAllMedicalRecords();

  /**
   Add a new medical record of a person.

   @param medicalRecord the medical record

   @return boolean, true if the add method of repository return true, else false

   @see fr.lyline.SafetyAlerts.repository.MedicalRecordRepo
   */
  boolean addMedicalRecord(MedicalRecord medicalRecord);

  /**
   Update the medical record of a person.

   @param medicalRecordToUpDate the medical record to update of this person

   @return boolean, true if the update method of repository return true, else false

   @see fr.lyline.SafetyAlerts.repository.MedicalRecordRepo
   */
  boolean updateMedicalRecord(MedicalRecord medicalRecordToUpDate);

  /**
   Remove the medical record by first name and last name of a person.

   @param firstName the first name of this person
   @param lastName  the last name of this person

   @return the boolean

   @see fr.lyline.SafetyAlerts.repository.MedicalRecordRepo
   */
  boolean removeMedicalRecordByFirstNameAndLastName(String firstName, String lastName);
}
