package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.repository.MedicalRecordRepoImpl;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MedicalRecordServiceTest {
  private final List<MedicalRecord> medicList = new ArrayList<>();
  MedicalRecordRepoImpl repository = mock(MedicalRecordRepoImpl.class);
  MedicalRecordServiceImpl classUnderTest = new MedicalRecordServiceImpl(repository);
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
  void whenGetValidMedicalRecordByFirstNameAndLastNameReturnThisRecord() {
    //Given
    when(repository.findByFirstNameAndLastName("Marge", "Simpson")).thenReturn(medic2);

    //When
    MedicalRecord actual = classUnderTest.getMedicalRecordByFirstNameAndLastName("Marge", "Simpson");

    //Then
    assertSame(medic2, actual);
  }

  @Test
  void whenGetMedicalRecordByFirstNameAndLastNameNotValidReturnNull() {
    //Given
    when(repository.findByFirstNameAndLastName("John", "Doe")).thenReturn(null);

    //When
    MedicalRecord actual = classUnderTest.getMedicalRecordByFirstNameAndLastName("John", "Doe");

    //Then
    assertNull(actual);
  }

  @Test
  void whenGetAllMedicalRecordsValidReturnThreeResults() {
    //Given
    when(repository.findAll()).thenReturn(medicList);

    //When
    List<MedicalRecord> actual = classUnderTest.getAllMedicalRecords();
    //Then
    assertEquals(3, actual.size());
  }

  @Test
  void whenGetAllMedicalRecordsNotValidReturnEmptyList() {
    //Given
    when(repository.findAll()).thenReturn(List.of());

    //When
    List<MedicalRecord> actual = classUnderTest.getAllMedicalRecords();

    //Then
    assertTrue(actual.isEmpty());
  }

  @Test
  void whenAddValidMedicalRecordReturnTrue() {
    //Given
    MedicalRecord medic = new MedicalRecord("John", "Doe", new DateTime("1956-05-12"),
        new String[]{}, new String[]{});
    when(repository.add(medic)).thenReturn(true);

    //When
    boolean actual = classUnderTest.addMedicalRecord(medic);

    //Then
    assertTrue(actual);
  }

  @Test
  void whenAddNotValidMedicalRecordReturnFalse() {
    //Given
    MedicalRecord medRecord = new MedicalRecord();
    medRecord.setFirstName("John");
    medRecord.setLastName("Doe");

    //When
    boolean actual = classUnderTest.addMedicalRecord(medRecord);

    //Then
    assertFalse(actual);
  }

  @Test
  void whenAddExistingMedicalRecordReturnFalse() {
    //Given
    when(repository.add(medic1)).thenReturn(false);

    //When
    boolean actual = classUnderTest.addMedicalRecord(medic1);

    //Then
    assertFalse(actual);
  }

  @Test
  void whenUpdateValidMedicalRecordReturnTrue() {
    //Given
    MedicalRecord medic = new MedicalRecord("Homer", "Simpson", new DateTime("1956-05-01"),
        new String[]{"vegetables"}, new String[]{""});
    when(repository.findByFirstNameAndLastName("Homer", "Simpson")).thenReturn(medic1);

    //When
    boolean actual = classUnderTest.updateMedicalRecord(medic);

    //Then
    assertTrue(actual);
  }

  @Test
  void whenUpdateNotValidMedicalRecordReturnFalse() {
    //Given
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setFirstName("Homer");
    medicalRecord.setLastName("Simpson");
    when(repository.findByFirstNameAndLastName("Homer", "Simpson")).thenReturn(medic1);

    //When
    boolean actual = classUnderTest.updateMedicalRecord(medicalRecord);

    //Then
    assertFalse(actual);
  }

  @Test
  void whenUpDateNotExistMedicalRecordReturnFalse() {
    //Given
    MedicalRecord medic = new MedicalRecord("John", "Doe", new DateTime("1956-05-01"),
        new String[]{"vegetables"}, new String[]{""});
    when(repository.findByFirstNameAndLastName("John", "Doe")).thenReturn(null);

    //When
    boolean actual = classUnderTest.updateMedicalRecord(medic);

    //Then
    assertFalse(actual);
  }

  @Test
  void whenRemoveExistingMedicalRecordByFirstNameAndLastNameReturnTrue() {
    //Given
    when(repository.findByFirstNameAndLastName("Homer", "Simpson")).thenReturn(medic1);
    //When
    boolean actual = classUnderTest.removeMedicalRecordByFirstNameAndLastName("Homer", "Simpson");
    //Then
    assertTrue(actual);
  }

  @Test
  void whenRemoveNotExistingMedicalRecordByFirstNameAndLastNameReturnFalse() {
    //Given
    when(repository.findByFirstNameAndLastName("John", "Doe")).thenReturn(null);
    //When
    boolean actual = classUnderTest.removeMedicalRecordByFirstNameAndLastName("john", "Doe");
    //Then
    assertFalse(actual);
  }

}
