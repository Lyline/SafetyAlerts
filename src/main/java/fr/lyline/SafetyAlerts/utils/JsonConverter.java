package fr.lyline.SafetyAlerts.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lyline.SafetyAlerts.model.FireStation;
import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.model.Person;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonConverter {
  public JsonConverter() {
  }

  public Map<String, Object> convertJsonToObject(String filePath) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new DateTimeModule());

    Map<String, Object> map = new HashMap<>();
    String data = "";

    try {
      data = new String(Files.readAllBytes(Paths.get(filePath)));
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      if (filePath.contains("personsTest")) {
        List<Person> value = Arrays.asList(mapper.readValue(data, Person[].class));
        for (Person person : value) {
          map.put(person.getFirstName() + person.getLastName(), person);
        }
      } else if (filePath.contains("fireStation")) {
        List<FireStation> value = Arrays.asList(mapper.readValue(data, FireStation[].class));
        for (int i = 0; i < value.size(); i++) {
          map.put(String.valueOf(i), value.get(i));
        }
      } else if (filePath.contains("medicalRecord")) {
        List<MedicalRecord> value = Arrays.asList(mapper.readValue(data, MedicalRecord[].class));                                                //
        for (MedicalRecord medicalRecord : value) {
          map.put(medicalRecord.getFirstName() + medicalRecord.getLastName(), medicalRecord);
        }
      }
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return map;
  }

  public void convertObjectToJson(String filePath, List<Object> objectList) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new DateTimeModule());

    try {
      mapper.writeValue(new File(filePath), objectList);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
