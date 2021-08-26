package fr.lyline.SafetyAlerts.controller;

import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicalRecordController {
  @Autowired
  MedicalRecordService service;

  @GetMapping("/medicalrecords")
  public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {
    List<MedicalRecord> response = service.getAllMedicalRecords();
    if (!response.isEmpty()) {
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @GetMapping("/medicalrecords/{firstName}_{lastName}")
  public ResponseEntity<MedicalRecord> getMedicalRecord(@PathVariable(value = "firstName") String firstName,
                                                        @PathVariable(value = "lastName") String lastName) {
    MedicalRecord response = service.getMedicalRecordById(firstName + lastName);
    if (response != null) {
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  @PostMapping("/medicalrecords")
  public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
    MedicalRecord medicalRecordIsExist =
        service.getMedicalRecordById(medicalRecord.getFirstName() + medicalRecord.getLastName());

    if (medicalRecordIsExist == null) {
      MedicalRecord response = service.addMedicalRecord(medicalRecord);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } else return new ResponseEntity<>(medicalRecordIsExist, HttpStatus.CONFLICT);
  }

  @PatchMapping("/medicalrecords/{firstName}_{lastName}")
  public ResponseEntity<MedicalRecord> upDateMedicalRecord(@PathVariable(value = "firstName") String firstName,
                                                           @PathVariable(value = "lastName") String lastName,
                                                           @RequestBody MedicalRecord medicalRecordToUpdate) {
    MedicalRecord medicalRecordIsExist = service.getMedicalRecordById(firstName + lastName);
    if (medicalRecordIsExist != null) {
      MedicalRecord response = service.upDateMedicalRecord(firstName + lastName, medicalRecordToUpdate);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
  }

  @DeleteMapping("/medicalrecords/{firstName}_{lastName}")
  public ResponseEntity deleteMedicalRecord(@PathVariable(value = "firstName") String firstName,
                                            @PathVariable(value = "lastName") String lastName) {
    MedicalRecord medicalRecordIsExist = service.getMedicalRecordById(firstName + lastName);
    if (medicalRecordIsExist != null) {
      service.removeMedicalRecordById(firstName + lastName);
      return new ResponseEntity(HttpStatus.OK);
    } else return new ResponseEntity(HttpStatus.NOT_FOUND);
  }
}
