package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.utils.JsonConverter;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MedicalRecordRepoTest {
  private final List<MedicalRecord> medicList = new ArrayList<>();
  JsonConverter data = mock(JsonConverter.class);
  private final MedicalRecordRepoImpl classUnderTest = new MedicalRecordRepoImpl(data);
  String fileJsonPath = "src/main/resources/medicalRecord.json";
  MedicalRecord medic1 = new MedicalRecord("Homer", "Simpson", new DateTime("1956-05-12"),
      new String[]{"duff 250cl"}, new String[]{"work"});
  MedicalRecord medic2 = new MedicalRecord("Marge", "Simpson", new DateTime("1957-03-19"),
      new String[]{}, new String[]{});
  MedicalRecord medic3 = new MedicalRecord("Bart", "Simpson", new DateTime("2010-02-23"),
      new String[]{}, new String[]{"school", "vegetables"});

  @BeforeEach
  void setUp() {
    medicList.add(medic1);
    medicList.add(medic2);
    medicList.add(medic3);
  }

  @Test
  void whenGetAllMedicalRecordsReturnThreeRecords() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(medicList);
    //When
    List<MedicalRecord> actual = classUnderTest.findAll();

    //Then
    assertEquals(3, actual.size());
  }

  @Test
  void whenGetAllMedicalRecordsNoContentReturnAnEmptyList() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(List.of());
    //When
    List<MedicalRecord> actual = classUnderTest.findAll();

    //Then
    assertTrue(actual.isEmpty());
  }

  @Test
  void whenGetValidMedicalRecordByFirstNameAndLastNameReturnThisRecord() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(medicList);
    //When
    MedicalRecord actual = classUnderTest.findByFirstNameAndLastName("Homer", "Simpson");

    //Then
    assertSame(medic1, actual);
  }

  @Test
  void whenGetNotValidMedicalRecordByFirstNameAndLastNameReturnNull() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(medicList);
    //When
    MedicalRecord actual = classUnderTest.findByFirstNameAndLastName("John", "Doe");

    //Then
    assertNull(actual);
  }

  @Test
  void whenAddValidMedicalRecordReturnTrue() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(medicList);
    MedicalRecord medic = new MedicalRecord("John", "Doe", new DateTime("1956-05-12"),
        new String[]{""}, new String[]{""});

    //When
    boolean actual = classUnderTest.add(medic);

    //Then
    assertTrue(actual);
  }

  @Test
  void whenAddExistingMedicalRecordReturnFalse() {
    //Given
    MedicalRecord medic = new MedicalRecord();
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(medicList);
    //when(classUnderTest.findMedic(medic)).thenReturn(null);
    //When
    boolean actual = classUnderTest.add(medic1);

    //Then
    assertFalse(actual);
  }

  @Test
  void whenUpdateValidMedicalRecordReturnTrue() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(medicList);
    MedicalRecord medicToUpdate = new MedicalRecord("Homer", "Simpson", new DateTime("1956-05-12"),
        new String[]{"coca 3ml"}, new String[]{"bees"});

    //When
    boolean actual = classUnderTest.update(medicToUpdate);

    //Then
    assertTrue(actual);
  }

  @Test
  void whenUpdateNotValidMedicalRecordReturnFalse() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(medicList);
    MedicalRecord medicToUpdate = new MedicalRecord("John", "Doe", new DateTime("1956-05-12"),
        new String[]{}, new String[]{});

    //When
    boolean actual = classUnderTest.update(medicToUpdate);

    //Then
    assertFalse(actual);
  }

  @Test
  void whenDeleteExistingMedicalRecordReturnTrue() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(medicList);

    //When
    boolean actual = classUnderTest.deleteByFirstNameAndLastName("Homer", "Simpson");

    //Then
    assertTrue(actual);
  }

  @Test
  void whenDeleteNotExistingMedicalRecordReturnFalse() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(medicList);

    //When
    boolean actual = classUnderTest.deleteByFirstNameAndLastName("John", "Doe");

    //Then
    assertFalse(actual);
  }
}
