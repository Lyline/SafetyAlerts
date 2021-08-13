package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.repository.MedicalRecordRepoImpl;
import org.assertj.core.util.Arrays;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MedicalRecordServiceTest {

  DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy");
  @Autowired
  private MedicalRecordService classUnderTest;
  @Autowired
  private MedicalRecordRepoImpl repository;

  @BeforeEach
  void setUp() {
    repository = new MedicalRecordRepoImpl();
    classUnderTest = new MedicalRecordServiceImpl(repository);
  }

  @Test
  void getMedicalRecordByIdValid() {
    //Given
    //When
    MedicalRecord actual = classUnderTest.getMedicalRecordById("JohnBoyd");
    //Then
    assertEquals("John", actual.getFirstName());
    assertEquals("Boyd", actual.getLastName());
  }

  @Test
  void getMedicalRecordByIdNotValid() {
    //Given
    //When
    MedicalRecord actual = classUnderTest.getMedicalRecordById("");
    //Then
    assertNull(actual);
  }

  @Test
  void getAllMedicalRecordsValid() {
    //Given
    //When
    List<MedicalRecord> actual = classUnderTest.getAllMedicalRecords();
    //Then
    assertEquals(23, actual.size());
  }

  @Test
  void getAllMedicalRecordsReturnEmptyList() {
    //Given
    repository.deleteAll();
    //When
    List<MedicalRecord> actual = classUnderTest.getAllMedicalRecords();
    //Then
    assertTrue(actual.isEmpty());
  }

  @Test
  void addMedicalRecordToRepositoryValidate() {
    //Given
    MedicalRecord medRecord = new MedicalRecord();
    medRecord.setFirstName("John");
    medRecord.setLastName("Doe");
    medRecord.setBirthdate(new DateTime().withDate(2021, 1, 1));
    medRecord.setMedications(new String[]{"aspirin"});
    medRecord.setAllergies(new String[]{"nuts"});
    //When
    classUnderTest.addMedicalRecord(medRecord);
    //Then
    MedicalRecord actual = classUnderTest.getMedicalRecordById("JohnDoe");
    assertEquals(medRecord, actual);
  }

  @Test
  void addMedicalRecordToRepositoryNotValidate() {
    //Given
    MedicalRecord medRecord = new MedicalRecord();
    medRecord.setFirstName("John");
    medRecord.setLastName("Doe");
    //When
    classUnderTest.addMedicalRecord(medRecord);
    //Then
    MedicalRecord actual = classUnderTest.getMedicalRecordById("JohnDoe");
    assertNull(actual);
  }

  @Test
  void addTwoValidMedicalRecordToRepositoryValidate() {
    //Given
    MedicalRecord med1 = new MedicalRecord();
    med1.setFirstName("John");
    med1.setLastName("Doe");
    med1.setBirthdate(new DateTime().withDate(2021, 1, 1));

    MedicalRecord med2 = new MedicalRecord();
    med2.setFirstName("Jane");
    med2.setLastName("Doe");
    med2.setBirthdate(new DateTime().withDate(1980, 10, 11));

    List<MedicalRecord> list = new ArrayList<>();
    list.add(med1);
    list.add(med2);
    //When
    classUnderTest.addAllMedicalRecords(list);
    //Then
    MedicalRecord actual1 = classUnderTest.getMedicalRecordById("JohnDoe");
    MedicalRecord actual2 = classUnderTest.getMedicalRecordById("JaneDoe");
    assertEquals(med1, actual1);
    assertEquals(med2, actual2);
  }

  @Test
  void addOneValidMedicalRecordAndOneNotValidToRepositoryValidate() {
    //Given
    MedicalRecord med1 = new MedicalRecord();
    med1.setFirstName("John");
    med1.setLastName("Doe");
    med1.setBirthdate(new DateTime().withDate(2021, 1, 1));

    MedicalRecord med2 = new MedicalRecord();
    med2.setFirstName("Jane");
    med2.setLastName("Doe");

    List<MedicalRecord> list = new ArrayList<>();
    list.add(med1);
    list.add(med2);
    //When
    classUnderTest.addAllMedicalRecords(list);
    //Then
    MedicalRecord actual1 = classUnderTest.getMedicalRecordById("JohnDoe");
    MedicalRecord actual2 = classUnderTest.getMedicalRecordById("JaneDoe");
    assertEquals(med1, actual1);
    assertNull(actual2);
  }

  @Test
  void upDateMedicalRecordToRepositoryValidate() {
    //Given
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setFirstName("John");
    medicalRecord.setLastName("Boyd");
    medicalRecord.setBirthdate(new DateTime().withDate(2021, 1, 1));
    medicalRecord.setAllergies(new String[]{"sun"});
    medicalRecord.setMedications(new String[]{"new medication"});
    //When
    classUnderTest.upDateMedicalRecord("JohnBoyd", medicalRecord);
    //Then
    MedicalRecord actual = classUnderTest.getMedicalRecordById("JohnBoyd");
    assertEquals("01/01/2021", actual.getBirthdate().toString(dtf));
    assertArrayEquals(Arrays.array("sun"), actual.getAllergies());
    assertArrayEquals(Arrays.array("new medication"), actual.getMedications());
  }

  @Test
  void upDateMedicalRecordToRepositoryNotValidate() {
    //Given
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setFirstName("John");
    medicalRecord.setLastName("Doe");
    //When
    classUnderTest.upDateMedicalRecord("JohnBoyd", medicalRecord);
    //Then
    MedicalRecord actual = classUnderTest.getMedicalRecordById("JohnDoe");
    assertNull(actual);
  }

  @Test
  void upDateNotExistMedicalRecordToRepository() {
    //Given
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setFirstName("John");
    medicalRecord.setLastName("Doe");
    medicalRecord.setAllergies(new String[]{"sun"});
    //When
    classUnderTest.upDateMedicalRecord("JohnDoe", medicalRecord);
    //Then
    MedicalRecord actual = classUnderTest.getMedicalRecordById("JohnDoe");
    assertNull(actual);
  }

  @Test
  void removeMedicalRecordByIdToRepository() {
    //Given
    //When
    classUnderTest.removeMedicalRecordById("JohnBoyd");
    //Then
    MedicalRecord actual = classUnderTest.getMedicalRecordById("JohnBoyd");
    assertNull(actual);
  }

  @Test
  void removeAllMedicalRecordsToRepository() {
    //Given
    //When
    classUnderTest.removeAllMedicalRecords();
    //Then
    List<MedicalRecord> actual = classUnderTest.getAllMedicalRecords();
    assertTrue(actual.isEmpty());
  }
}
