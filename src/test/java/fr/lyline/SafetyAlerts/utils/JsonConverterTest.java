package fr.lyline.SafetyAlerts.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lyline.SafetyAlerts.model.FireStation;
import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JsonConverterTest {
  JsonConverter jsConverter = new JsonConverter();

  ObjectMapper mapper = new ObjectMapper();
  SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

  Person person = new Person();
  FireStation fireStation = new FireStation();
  MedicalRecord medicalRecord = new MedicalRecord();

  @Test
  void shouldConvertJsonEntryToPersonObject() {
    //Given
    String dataTest = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\"," +
        "\"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" }";

    //When
    try {
      person = mapper.readValue(dataTest, Person.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    //Then
    assertEquals("John", person.getFirstName());
    assertEquals("Boyd", person.getLastName());
    assertEquals("1509 Culver St", person.getAddress());
    assertEquals("Culver", person.getCity());
    assertEquals(97451, person.getZip());
    assertEquals("841-874-6512", person.getPhone());
    assertEquals("jaboyd@email.com", person.getEmail());
  }

  @Test
  void shouldConvertJsonEntryToFireStationObject() {
    //Given
    String dataTest = "{ \"address\":\"1509 Culver St\", \"station\":\"3\" }";

    //When
    try {
      fireStation = mapper.readValue(dataTest, FireStation.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    //Then
    assertEquals("1509 Culver St", fireStation.getAddress());
    assertEquals(3, fireStation.getStation());
  }

  @Test
  void shouldConvertJsonEntryToMedicalRecordObject() {
    //Given
    String dataTest = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1984\", " +
        "\"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] }";

    //When
    try {
      mapper.setDateFormat(df);
      medicalRecord = mapper.readerFor(MedicalRecord.class).readValue(dataTest);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    //Then
    assertEquals("John", medicalRecord.getFirstName());
    assertEquals("Boyd", medicalRecord.getLastName());
    assertEquals("03/06/1984", df.format(medicalRecord.getBirthdate()));
    assertArrayEquals(new String[]{"aznol:350mg", "hydrapermazol:100mg"}, medicalRecord.getMedications());
    assertArrayEquals(new String[]{"nillacilan"}, medicalRecord.getAllergies());
  }

  @Test
  void shouldNotConvertJsonEntryBadFormatJson() {
    //Given
    String dataTest = "{ \"Name\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\"," +
        "\"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" }";

    //When
    try {
      person = mapper.readValue(dataTest, Person.class);
    } catch (JsonProcessingException e) {
      System.out.println("Unrecognized Json or Java field ");
    }

    //Then
    assertThrows(JsonProcessingException.class, () -> {
      mapper.readValue(dataTest, Person.class);
    });
  }

  @Test
  void shouldCreateTwoPersonObjectsFromJsonFile() {
    //Given
    Map<String, Object> list;

    //When
    list = jsConverter.convertJsonToObject("src/test/resources/personsTest.json");
    System.out.println(list);
    System.out.println(list.get("FeliciaBoyd"));
    //Then
    assertEquals(2, list.size());
  }

  @Test
  void shouldCreateFourFireStationObjectsFromJsonFile() {
    //Given
    Map<String, Object> list;

    //When
    list = jsConverter.convertJsonToObject("src/test/resources/fireStationTest.json");

    //Then
    assertEquals(4, list.size());
  }

  @Test
  void shouldCreateTwoMedicalRecordObjectsFromJsonFile() {
    //Given
    Map<String, Object> list;

    //When
    list = jsConverter.convertJsonToObject("src/test/resources/medicalRecordTest.json");

    //Then
    assertEquals(2, list.size());
  }
}
