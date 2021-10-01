package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.utils.JsonConverter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalRecordRepoImpl implements MedicalRecordRepo {
  JsonConverter data;
  String fileJsonPath = "src/main/resources/medicalRecord.json";

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
