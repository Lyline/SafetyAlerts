package fr.lyline.SafetyAlerts.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lyline.SafetyAlerts.model.FireStation;
import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 The JsonConverter class can deserialize to Person, FireStation or MedicalRecord model and can serialize them.

 @param <T> the generic type parameter

 @author Quesne GC
 @since 0.1 */
@Component
public class JsonConverter<T> {
  Logger logger = LogManager.getLogger(JsonConverter.class);

  /**
   Instantiates a new Json converter.
   */
  public JsonConverter() {
  }

  /**
   Convert json to generic object (Person, MedicalRecord or FireStation).

   @param <T>           the type parameter
   @param inputFilePath the input file path of json data

   @return the generic object
   */
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
    } catch (Exception e) {
      logger.error(e.getMessage());
      logger.debug("test" + e.fillInStackTrace());
      logger.trace(e);
    }
    return (T) list;
  }

  /**
   Convert object to json and print into data file.

   @param outputFilePath the output file path of json data
   @param objectList     the object list (Person, MedicalRecord or FireStation)
   */
  public void convertObjectToJson(String outputFilePath, List<T> objectList) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new DateTimeModule());

    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFilePath), objectList);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   Read data and convert to string.

   @param filePath the file path of data file

   @return the data to string
   */
  public String readData(String filePath) {
    String data = "";
    try {
      data = new String(Files.readAllBytes(Paths.get(filePath)));

    } catch (IOException e) {
      logger.error("File cannot read or not exist - " + e.getMessage());
    }
    return data;
  }
}
