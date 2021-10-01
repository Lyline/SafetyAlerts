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
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonConverter<T> {
  public JsonConverter() {
  }

  public <T> T convertJsonToObject(String inputFilePath) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new DateTimeModule());

    List<T> list = new ArrayList<>();
    String data = "";

    data = readData(inputFilePath);

    try {
      if (inputFilePath.contains("person")) {
        Person[] value = mapper.readValue(data, Person[].class);
        for (Person person : value) {
          list.add((T) person);
        }
      } else if (inputFilePath.contains("fireStation")) {
        FireStation[] value = mapper.readValue(data, FireStation[].class);
        for (FireStation fireStation : value) {
          list.add((T) fireStation);
        }
      } else if (inputFilePath.contains("medicalRecord")) {
        MedicalRecord[] value = mapper.readValue(data, MedicalRecord[].class);                                                //
        for (MedicalRecord medicalRecord : value) {
          list.add((T) medicalRecord);
        }
      }
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return (T) list;
  }

  public void convertObjectToJson(String outputFilePath, List<T> objectList) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new DateTimeModule());

    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFilePath), objectList);
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
