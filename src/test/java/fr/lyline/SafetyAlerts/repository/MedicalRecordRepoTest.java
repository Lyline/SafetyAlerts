package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.MedicalRecord;
import org.assertj.core.util.Arrays;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MedicalRecordRepoTest {
  MedicalRecord medicalRecord = new MedicalRecord();
  DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy");
  private MedicalRecordRepoImpl classUnderTest = new MedicalRecordRepoImpl();

  @BeforeEach
  void setUp() {
    classUnderTest = new MedicalRecordRepoImpl();
  }

  @Test
  void getAllMedicalRecordsTest() {
    //Given

    //When
    List<MedicalRecord> result = classUnderTest.findAll();

    //Then
    assertEquals(23, result.size());
    assertEquals("class fr.lyline.SafetyAlerts.model.MedicalRecord", result.get(1).getClass().toString());
  }

  @Test
  void getMedicalRecordByIdTest() {
    //Given

    //When
    MedicalRecord result = classUnderTest.findById("JohnBoyd");

    //Then
    assertEquals("John", result.getFirstName());
    assertEquals("Boyd", result.getLastName());
    assertEquals("03/06/1984", result.getBirthdate().toString(dtf));
    assertArrayEquals(Arrays.array("aznol:350mg", "hydrapermazol:100mg"), result.getMedications());
    assertArrayEquals(Arrays.array("nillacilan"), result.getAllergies());
  }

  @Test
  void addMedicalRecordTest() {
    //Given
    medicalRecord.setFirstName("Jean");
    medicalRecord.setLastName("Bon");
    medicalRecord.setBirthdate(new DateTime().withDate(2021, 12, 31));
    medicalRecord.setMedications(new String[]{"new medication", "aspirin"});
    medicalRecord.setAllergies(new String[]{"coconut"});

    //When
    classUnderTest.add(medicalRecord);

    //Then
    MedicalRecord result = classUnderTest.findById("JeanBon");
    assertEquals("Jean", result.getFirstName());
    assertEquals("Bon", result.getLastName());
    assertEquals("12/31/2021", result.getBirthdate().toString(dtf));
    assertArrayEquals(Arrays.array("new medication", "aspirin"), result.getMedications());
    assertArrayEquals(Arrays.array("coconut"), result.getAllergies());
  }

  @Test
  void addAllMedicalRecordTest() {
    //Given
    medicalRecord.setFirstName("Marcel");
    medicalRecord.setLastName("Dugenou");

    MedicalRecord medicalRecord1 = new MedicalRecord();
    medicalRecord1.setFirstName("Jane");
    medicalRecord1.setLastName("Doe");

    List<MedicalRecord> list = new ArrayList<>();
    list.add(medicalRecord);
    list.add(medicalRecord1);

    //When
    classUnderTest.addAll(list);

    //Then
    List<MedicalRecord> result = classUnderTest.findAll();
    assertTrue(result.contains(medicalRecord));
    assertTrue(result.contains(medicalRecord1));
  }

  @Test
  void upDateMedicalRecordTest() {
    //Given
    medicalRecord = classUnderTest.findById("JohnBoyd");
    medicalRecord.setMedications(new String[]{"reset data"});

    //When
    classUnderTest.update("JohnBoyd", medicalRecord);

    //Then
    MedicalRecord result = classUnderTest.findById("JohnBoyd");

    assertEquals("John", result.getFirstName());
    assertEquals("Boyd", result.getLastName());
    assertEquals("03/06/1984", result.getBirthdate().toString(dtf));
    assertArrayEquals(Arrays.array("reset data"), result.getMedications());
  }

  @Test
  void deleteMedicalRecordTest() {
    //Given

    //When
    classUnderTest.deleteById("JohnBoyd");
    //Then
    assertEquals(null, classUnderTest.findById("JohnBoyd"));
  }

  @Test
  void deleteAllMedicalRecordTest() {
    //Given

    //When
    classUnderTest.deleteAll();
    //Then
    assertTrue(classUnderTest.findAll().isEmpty());
  }
}
