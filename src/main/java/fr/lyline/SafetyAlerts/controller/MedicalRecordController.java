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

@RestController
public class MedicalRecordController {
  @Autowired
  MedicalRecordService service;

  Logger logger = LogManager.getLogger(MedicalRecordController.class);

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
      logger.warn("PATCH /medicalrecords/" + firstName + "_" + lastName + " - Medical record not exist - Status 404");
      logger.info("null");
      return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
    }
  }

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
