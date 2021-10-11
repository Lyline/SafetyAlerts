package fr.lyline.SafetyAlerts.controller;

import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 The Medical record controller of the CRUD application.

 @author Quesne GC
 @see fr.lyline.SafetyAlerts.service.MedicalRecordService
 @since 0.1 */
@RestController
public class MedicalRecordController {
  /**
   The Medical record Service.

   @see fr.lyline.SafetyAlerts.service.MedicalRecordService
   */
  @Autowired
  MedicalRecordService service;

  /**
   The Logger.

   @see org.apache.logging.log4j.LogManager;
   @see org.apache.logging.log4j.Logger;
   */
  Logger logger = LogManager.getLogger(MedicalRecordController.class);

  /**
   Gets all medical records and a HttpStatus 200 when the service return result, else it return null and a HttpStatus 404.

   @return the all medical records
   */
  @GetMapping("/medicalrecords")
  public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {
    List<MedicalRecord> response = service.getAllMedicalRecords();

    if (!response.isEmpty()) {
      logger.info("GET /medicalrecords : " + response.size() + " medical record(s) - Status 200");
      logger.info(response);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      logger.warn("GET /medicalrecords : No Data - Status 404");
      logger.info("null");
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  /**
   Gets the medical record of this person when the first name and the last name entered in parameters. It returns this
   medical record and a HttpStatus 200 if this person exist, else it returns null and a HttpStatus 404.

   @param firstName the first name of this person
   @param lastName  the last name of this person

   @return the medical record
   */
  @GetMapping("/medicalrecords/{firstName}_{lastName}")
  public ResponseEntity<MedicalRecord> getMedicalRecord(@PathVariable(value = "firstName") String firstName,
                                                        @PathVariable(value = "lastName") String lastName) {
    MedicalRecord response = service.getMedicalRecordByFirstNameAndLastName(firstName, lastName);
    if (response != null) {
      logger.info("GET /medicalrecords/" + response.getFirstName() + "_" + response.getLastName() + " - Status 200");
      logger.info(response);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      logger.warn("GET /medicalrecords/" + firstName + "_" + lastName + " - Medical record not exist - Status 404");
      logger.info(response);
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  /**
   Add a new medical record when the medical record entered in parameter. If the medical record is completed, it return
   the medical record and a HttpSatus 200, else if the medical record is uncompleted then it return null and a HttpStatus
   404, or if the medical record already exist then it returns null and a HttpStatut 409.

   @param medicalRecord the medical record

   @return the added medical record
   */
  @PostMapping("/medicalrecords")
  public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
    MedicalRecord medicalRecordIsExist =
        service.getMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());

    if (medicalRecord.getFirstName() == null | medicalRecord.getLastName() == null |
        medicalRecord.getBirthdate() == null | medicalRecord.getMedications() == null |
        medicalRecord.getAllergies() == null) {
      logger.warn("POST /medicalrecords " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName() +
          " - Incomplete informations for creation - Status 404");
      logger.info(medicalRecord);
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    if (medicalRecordIsExist == null) {
      service.addMedicalRecord(medicalRecord);
      logger.info("POST /medicalrecords : " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName() + " - Status 201");
      logger.info(medicalRecord);
      return new ResponseEntity<>(medicalRecord, HttpStatus.CREATED);
    } else {
      logger.warn("POST /medicalrecords : " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName() + " - Medical " +
          "record already exist - Status 409");
      logger.info("null");
      return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }
  }

  /**
   Update a medical record when the first name, the last name of this person and the medical record to update entered in
   parameters. If the medical record exist then it returns the updated medical record and a HttpStatus 200, else it returns
   null and a HttpStatus 304

   @param firstName             the first name
   @param lastName              the last name
   @param medicalRecordToUpdate the medical record to update

   @return the response entity
   */
  @PatchMapping("/medicalrecords/{firstName}_{lastName}")
  public ResponseEntity<MedicalRecord> upDateMedicalRecord(@PathVariable(value = "firstName") String firstName,
                                                           @PathVariable(value = "lastName") String lastName,
                                                           @RequestBody MedicalRecord medicalRecordToUpdate) {
    MedicalRecord medicalRecordIsExist = service.getMedicalRecordByFirstNameAndLastName(firstName, lastName);

    if (medicalRecordIsExist != null) {
      service.updateMedicalRecord(medicalRecordToUpdate);
      logger.info("PATCH /medicalrecord/" + firstName + "_" + lastName + " - Status 200");
      logger.info("Exist : " + medicalRecordIsExist + "\nUpdate : " + medicalRecordToUpdate);
      return new ResponseEntity<>(medicalRecordToUpdate, HttpStatus.OK);
    } else {
      logger.warn("PATCH /medicalrecords/" + firstName + "_" + lastName + " - Medical record not exist - Status 304");
      logger.info("null");
      return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
    }
  }

  /**
   Delete a medical record when the first name and the last name of this person entered in parameters. If this medical record
   exist then it returns the deleted medical record and a HttpStatus 200, else it returns null and a HttpStatus 404.

   @param firstName the first name of the medical record to delete
   @param lastName  the last name of the medical record to delete

   @return the deleted medical record
   */
  @DeleteMapping("/medicalrecords/{firstName}_{lastName}")
  public ResponseEntity deleteMedicalRecord(@PathVariable(value = "firstName") String firstName,
                                            @PathVariable(value = "lastName") String lastName) {
    MedicalRecord medicalRecordIsExist = service.getMedicalRecordByFirstNameAndLastName(firstName, lastName);

    if (medicalRecordIsExist != null) {
      service.removeMedicalRecordByFirstNameAndLastName(firstName, lastName);
      logger.info("DELETE /medicalrecords/" + firstName + "_" + lastName + " - Status 200");
      logger.info(medicalRecordIsExist);
      return new ResponseEntity(medicalRecordIsExist, HttpStatus.OK);
    } else {
      logger.warn("DELETE /medicalrecords/" + firstName + "_" + lastName + " - Medical record not exist - Status 404");
      logger.info("null");
      return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }
  }
}
