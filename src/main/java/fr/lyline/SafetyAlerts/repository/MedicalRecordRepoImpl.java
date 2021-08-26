package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.utils.JsonConverter;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class MedicalRecordRepoImpl implements MedicalRecordRepo {
  JsonConverter data = new JsonConverter();
  private final Map<String, MedicalRecord> medicalMap =
      (Map<String, MedicalRecord>) data.convertJsonToObject("src/main/resources/medicalRecord.json");

  @Override
  public List<MedicalRecord> findAll() {
    return medicalMap.values().stream().collect(Collectors.toList());
  }

  @Override
  public MedicalRecord findById(String firstNameLastName) {
    return medicalMap.get(firstNameLastName);
  }

  @Override
  public MedicalRecord add(MedicalRecord medicalRecord) {
    medicalMap.put(medicalRecord.getFirstName() + medicalRecord.getLastName(), medicalRecord);
    return medicalRecord;
  }

  @Override
  public void addAll(List<MedicalRecord> list) {
    HashMap<String, MedicalRecord> subList = new HashMap<>();

    for (MedicalRecord mr : list) {
      subList.put(mr.getFirstName() + mr.getLastName(), mr);
    }

    medicalMap.putAll(subList);
  }

  @Override
  public MedicalRecord update(String id, MedicalRecord medicalRecordToUpDate) {
    MedicalRecord medicalRecord = medicalMap.get(id);
    medicalMap.replace(id, medicalRecord, medicalRecordToUpDate);
    return medicalRecord;
  }

  @Override
  public void deleteById(String id) {
    medicalMap.remove(id);
  }

  @Override
  public void deleteAll() {
    medicalMap.clear();
  }
}
