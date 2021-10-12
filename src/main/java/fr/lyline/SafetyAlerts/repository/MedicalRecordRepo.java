package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.MedicalRecord;

import java.util.List;

/**
 The interface of medical record repository.

 @author Quesne GC
 @since 0.1 */
public interface MedicalRecordRepo {
  /**
   Find all medical records.

   @return the list of medical records
   */
  List<MedicalRecord> findAll();

  /**
   Find the medical record object by first name and last name of this person.

   @param firstName the first name of this person
   @param lastName  the last name of this person

   @return the medical record
   */
  MedicalRecord findByFirstNameAndLastName(String firstName, String lastName);

  /**
   Add a new medical record in data.

   @param medicalRecord the medical record

   @return boolean, true if the add validate else false
   */
  boolean add(MedicalRecord medicalRecord);

  /**
   Update a medical record.

   @param medicalRecordToUpDate the medical record to update

   @return boolean, true if the update validate else false
   */
  boolean update(MedicalRecord medicalRecordToUpDate);

  /**
   Delete a medical record by first name and last name of this person.

   @param firstName the first name of this person
   @param lastName  the last name of this person

   @return boolean, true if the deletion validate else false
   */
  boolean deleteByFirstNameAndLastName(String firstName, String lastName);
}
