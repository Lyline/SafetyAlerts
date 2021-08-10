package fr.lyline.SafetyAlerts.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lyline.SafetyAlerts.model.FireStation;
import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.model.Person;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JsonConverter<T> {
  public JsonConverter() {
  }

  public <T> T convertJsonToObject(String inputFilePath) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new DateTimeModule());

    Map<String, T> map = new HashMap<>();
    String data = "";

    data = readData(inputFilePath);

    try {
      if (inputFilePath.contains("person")) {
        List<Person> value = Arrays.asList(mapper.readValue(data, Person[].class));
        for (Person person : value) {
          map.put(person.getFirstName() + person.getLastName(), (T) person);
        }
      } else if (inputFilePath.contains("fireStation")) {
        List<FireStation> value = Arrays.asList(mapper.readValue(data, FireStation[].class));
        for (FireStation fireStation : value) {
          map.put(fireStation.getStation() + "-" + fireStation.getAddress(), (T) fireStation);
        }
      } else if (inputFilePath.contains("medicalRecord")) {
        List<MedicalRecord> value = Arrays.asList(mapper.readValue(data, MedicalRecord[].class));                                                //
        for (MedicalRecord medicalRecord : value) {
          map.put(medicalRecord.getFirstName() + medicalRecord.getLastName(), (T) medicalRecord);
        }
      }
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return (T) map;
  }

  public void convertObjectToJson(String outputFilePath, List<T> objectList) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new DateTimeModule());

    try {
      mapper.writeValue(new File(outputFilePath), objectList);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public String readData(String filePath) {
    String data = "";
    try {
      data = new String(Files.readAllBytes(Paths.get(filePath)));

    } catch (IOException e) {
      e.printStackTrace();
    }
    return data;
  }
}
