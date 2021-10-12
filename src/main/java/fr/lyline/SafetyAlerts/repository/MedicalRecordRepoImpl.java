package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.utils.JsonConverter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 The implementation of medical record repository for json data.

 @author Quesne GC
 @since 0.1 */
@Repository
public class MedicalRecordRepoImpl implements MedicalRecordRepo {
  JsonConverter data;
  String fileJsonPath = "src/main/resources/medicalRecord.json";

  /**
   Instantiates a new Medical record repository.

   @param data the medical record object data
   */
  public MedicalRecordRepoImpl(JsonConverter data) {
    this.data = data;
  }

  @Override
  public List<MedicalRecord> findAll() {
    List<MedicalRecord> medicalRecordDataList = (List<MedicalRecord>) data.convertJsonToObject(fileJsonPath);
    return new ArrayList<>(medicalRecordDataList);
  }

  @Override
  public MedicalRecord findByFirstNameAndLastName(String firstName, String lastName) {
    return findMedic(firstName, lastName);
  }

  @Override
  public boolean add(MedicalRecord medicalRecord) {
    List<MedicalRecord> medicalRecordDataList = (List<MedicalRecord>) data.convertJsonToObject(fileJsonPath);
    MedicalRecord medicIsExist = findMedic(medicalRecord);

    if (medicIsExist == null) {
      medicalRecordDataList.add(medicalRecord);
      data.convertObjectToJson(fileJsonPath, medicalRecordDataList);
      return true;
    } else return false;
  }

  @Override
  public boolean update(MedicalRecord medicalRecordToUpDate) {
    List<MedicalRecord> medicalDataList = (List<MedicalRecord>) data.convertJsonToObject(fileJsonPath);
    MedicalRecord oldMedicalRecord = null;

    for (MedicalRecord mr : medicalDataList) {
      if (mr.getFirstName().equals(medicalRecordToUpDate.getFirstName()) &&
          mr.getLastName().equals(medicalRecordToUpDate.getLastName())) {
        oldMedicalRecord = mr;
      }
    }
    if (oldMedicalRecord != null) {
      medicalDataList.remove(oldMedicalRecord);
      medicalDataList.add(medicalRecordToUpDate);
      data.convertObjectToJson(fileJsonPath, medicalDataList);
      return true;
    } else return false;
  }

  @Override
  public boolean deleteByFirstNameAndLastName(String firstName, String lastName) {
    List<MedicalRecord> medicalDataList = (List<MedicalRecord>) data.convertJsonToObject(fileJsonPath);
    MedicalRecord medicalRecordToDelete = null;

    for (MedicalRecord medic : medicalDataList) {
      if (medic.getFirstName().equals(firstName) &&
          medic.getLastName().equals(lastName)) {
        medicalRecordToDelete = medic;
      }
    }

    if (medicalRecordToDelete != null) {
      medicalDataList.remove(medicalRecordToDelete);
      data.convertObjectToJson(fileJsonPath, medicalDataList);
      return true;
    } else return false;
  }

  /**
   Find the medical record and return this medical record if its exist in the database, when a medical record object entered
   in parameter.

   @param medic the medic

   @return the medical record recorded on the database
   */
  public MedicalRecord findMedic(MedicalRecord medic) {
    MedicalRecord medicSearch = null;
    List<MedicalRecord> medicalRecordDataList = (List<MedicalRecord>) data.convertJsonToObject(fileJsonPath);
    for (MedicalRecord mr : medicalRecordDataList) {
      if (mr.getFirstName().equals(medic.getFirstName()) &&
          mr.getLastName().equals(medic.getLastName())) {
        medicSearch = medic;
      }
    }
    return medicSearch;
  }

  /**
   Find a medical record of a person when the first name and the last name entered in parameter, and return this medical
   record object if its exist, else its return null.

   @param firstName the first name
   @param lastName  the last name

   @return the medical record
   */
  public MedicalRecord findMedic(String firstName, String lastName) {
    MedicalRecord medicSearch = null;
    List<MedicalRecord> medicalRecordDataList = (List<MedicalRecord>) data.convertJsonToObject(fileJsonPath);
    for (MedicalRecord mr : medicalRecordDataList) {
      if (mr.getFirstName().equals(firstName) &&
          mr.getLastName().equals(lastName)) {
        medicSearch = mr;
      }
    }
    return medicSearch;
  }
}
