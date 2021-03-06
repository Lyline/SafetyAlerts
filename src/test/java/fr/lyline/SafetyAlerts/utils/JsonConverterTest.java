package fr.lyline.SafetyAlerts.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lyline.SafetyAlerts.model.FireStation;
import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.model.Person;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
public class JsonConverterTest {

  @Autowired
  JacksonTester<Object> json;
  JsonConverter classUnderTest = new JsonConverter();

  String outputFilePath = "src/test/resources/result.json";

  ObjectMapper mapper = new ObjectMapper();

  Person person = new Person();
  FireStation fireStation = new FireStation();
  MedicalRecord medicalRecord = new MedicalRecord();


  @Test
  void shouldConvertJsonEntryToPersonObject() throws JsonProcessingException {
    //Given
    String dataTest = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\"," +
        "\"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" }";

    //When
    person = mapper.readValue(dataTest, Person.class);

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
  void shouldConvertJsonEntryToFireStationObject() throws JsonProcessingException {
    //Given
    String dataTest = "{ \"address\":\"1509 Culver St\", \"station\":\"3\" }";

    //When
    fireStation = mapper.readValue(dataTest, FireStation.class);

    //Then
    assertEquals("1509 Culver St", fireStation.getAddress());
    assertEquals(3, fireStation.getStation());
  }

  @Test
  void shouldConvertJsonEntryToMedicalRecordObject() throws JsonProcessingException {
    //Given
    String dataTest = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1984\", " +
        "\"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] }";

    //When
    medicalRecord = mapper.readValue(dataTest, MedicalRecord.class);

    //Then
    assertEquals("John", medicalRecord.getFirstName());
    assertEquals("Boyd", medicalRecord.getLastName());
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
      e.printStackTrace();
    }

    //Then
    assertThrows(JsonProcessingException.class, () -> {
      mapper.readValue(dataTest, Person.class);
    });
  }

  @Test
  void shouldCreateTwoPersonObjectsFromJsonFile() {
    //Given
    List<Person> personList;

    //When
    personList = (List<Person>) classUnderTest.convertJsonToObject("src/test/resources/personsTest.json");

    //Then
    assertEquals(2, personList.size());
  }

  @Test
  void shouldCreateFourFireStationObjectsFromJsonFile() {
    //Given
    List<FireStation> fireStationList;

    //When
    fireStationList = (List<FireStation>) classUnderTest.convertJsonToObject("src/test/resources/fireStationTest.json");

    //Then
    assertEquals(4, fireStationList.size());
  }

  @Test
  void shouldCreateTwoMedicalRecordObjectsFromJsonFile() {
    //Given
    List<MedicalRecord> medicalRecordList;

    //When
    medicalRecordList = (List<MedicalRecord>) classUnderTest.convertJsonToObject("src/test/resources/medicalRecordTest.json");

    //Then
    assertEquals(2, medicalRecordList.size());
  }

  @Test
  void shouldCreatePersonJsonFileFromToJavaObject() throws IOException {
    //Given
    List<Object> list = new ArrayList<>();

    person.setFirstName("Marcel");
    person.setLastName("Dugenou");

    Person person1 = new Person();
    person1.setFirstName("Jane");
    person1.setLastName("Doe");

    list.add(person);
    list.add(person1);

    //When
    classUnderTest.convertObjectToJson(outputFilePath, list);

    //Then
    assertThat(this.json.write(person1))
        .extractingJsonPathStringValue("firstName")
        .isEqualTo("Jane");
    assertThat(this.json.write(person))
        .extractingJsonPathStringValue("firstName")
        .isEqualTo("Marcel");
  }

  @Test
  void shouldCreateFireStationJsonFileFromToJavaObject() throws IOException {
    //Given
    List<FireStation> list = new ArrayList<>();

    fireStation.setStation(1);
    fireStation.setAddress("LalaLand Av");

    FireStation fireStation1 = new FireStation();
    fireStation1.setStation(2);
    fireStation1.setAddress("NoMansLand St");

    list.add(fireStation);
    list.add(fireStation1);

    //When
    classUnderTest.convertObjectToJson(outputFilePath, list);

    //Then
    assertThat(this.json.write(fireStation))
        .extractingJsonPathStringValue("address")
        .isEqualTo("LalaLand Av");
    assertThat(this.json.write(fireStation1))
        .extractingJsonPathStringValue("address")
        .isEqualTo("NoMansLand St");
  }

  @Test
  void shouldCreateMedicalRecordJsonFileFromToJavaObject() throws IOException, ParseException {
    //Given
    List<Object> list = new ArrayList<>();

    medicalRecord.setFirstName("Jane");
    medicalRecord.setLastName("Doe");
    medicalRecord.setBirthdate(new DateTime().withDate(2021, 06, 21));
    medicalRecord.setMedications(new String[]{"penicillin", "aspirin"});
    medicalRecord.setAllergies(new String[]{"lactose", "bee"});

    MedicalRecord medicalRecord1 = new MedicalRecord();

    medicalRecord1.setFirstName("Marcel");
    medicalRecord1.setLastName("Dugenou");

    list.add(medicalRecord);
    list.add(medicalRecord1);

    //When
    classUnderTest.convertObjectToJson(outputFilePath, list);

    //Then
    assertThat(this.json.write(medicalRecord))
        .extractingJsonPathStringValue("firstName")
        .isEqualTo("Jane");
    assertThat(this.json.write(medicalRecord))
        .extractingJsonPathStringValue("birthdate")
        .isEqualTo("06/21/2021");
    assertThat(this.json.write(medicalRecord1))
        .extractingJsonPathStringValue("firstName")
        .isEqualTo("Marcel");
  }

  @Test
  void shouldReadJsonEmptyFileReturnEmptyData() throws IOException {
    //Given
    String filePath = "src/test/resources/empty.json";
    //When
    String actual = classUnderTest.readData(filePath);
    //Then

    assertTrue(actual.isEmpty());
  }
}
