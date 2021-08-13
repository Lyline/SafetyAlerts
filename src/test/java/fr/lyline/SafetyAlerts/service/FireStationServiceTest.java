package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.FireStation;
import fr.lyline.SafetyAlerts.repository.FireStationRepoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FireStationServiceTest {

  @Autowired
  FireStationService classUnderTest;

  @Autowired
  FireStationRepoImpl repository;

  @BeforeEach
  void setUp() {
    repository = new FireStationRepoImpl();
    classUnderTest = new FireStationServiceImpl(repository);
  }

  @Test
  void getFireStationByValidAddress() {
    //Given
    //When
    List<Integer>
        actual = classUnderTest.getFireStation("112 Steppes Pl");
    //Then
    assertEquals(Arrays.asList(3, 4), actual);
  }

  @Test
  void getFireStationByIdNotValidAddress() {
    //Given
    //When
    List<Integer> actual = classUnderTest.getFireStation("");
    //Then
    assertTrue(actual.isEmpty());
  }

  @Test
  void getAllFireStations() {
    //Given
    //When
    List<Integer> actual = classUnderTest.getAllFireStations();
    //Then
    assertEquals(List.of(1, 2, 3, 4), actual);
  }

  @Test
  void getAllFireStationsReturnEmptyList() {
    //Given
    repository.deleteAll();
    //When
    List<Integer> actual = classUnderTest.getAllFireStations();
    //Then
    assertTrue(actual.isEmpty());
  }

  @Test
  void addAddressDependantFireStationToRepositoryValidate() {
    //Given
    FireStation fs = new FireStation();
    fs.setStation(5);
    fs.setAddress("NoWhere");
    //When
    classUnderTest.addFireStation(fs);
    //Then
    List<Integer> actual = classUnderTest.getFireStation("NoWhere");
    assertEquals(List.of(5), actual);
  }

  @Test
  void addAddressDependantFireStationToRepositoryNotValidate() {
    //Given
    FireStation fs = new FireStation();
    fs.setAddress("NoWhere");
    //When
    classUnderTest.addFireStation(fs);
    //Then
    List<Integer> actual = classUnderTest.getFireStation("NoWhere");
    assertTrue(actual.isEmpty());
  }

  @Test
  void addTwoAddressesDependantFireStationToRepositoryValidate() {
    //Given
    FireStation fs1 = new FireStation();
    fs1.setStation(1);
    fs1.setAddress("NoWhere");

    FireStation fs2 = new FireStation();
    fs2.setStation(2);
    fs2.setAddress("New address");

    List<FireStation> list = new ArrayList<>();
    list.add(fs1);
    list.add(fs2);
    //When
    classUnderTest.addAllFireStations(list);
    //Then
    List<Integer> actual1 = classUnderTest.getFireStation("NoWhere");
    List<Integer> actual2 = classUnderTest.getFireStation("New address");

    assertEquals(List.of(1), actual1);
    assertEquals(List.of(2), actual2);
  }

  @Test
  void addOneValideAddressAndOneNotValideAddressReturnOneValidAddress() {
    //Given
    FireStation fs1 = new FireStation();
    fs1.setStation(1);
    fs1.setAddress("NoWhere");

    FireStation fs2 = new FireStation();
    fs2.setAddress("New address");

    List<FireStation> list = new ArrayList<>();
    list.add(fs1);
    list.add(fs2);
    //When
    classUnderTest.addAllFireStations(list);
    //Then
    List<Integer> actual1 = classUnderTest.getFireStation("NoWhere");
    List<Integer> actual2 = classUnderTest.getFireStation("New address");

    assertEquals(List.of(1), actual1);
    assertTrue(actual2.isEmpty());
  }

  @Test
  void upDateAddressFireStationToRepositoryValide() {
    //Given
    FireStation fs = new FireStation();
    fs.setStation(1);
    fs.setAddress("1509 Culver St");
    //When
    classUnderTest.upDateFireStation("1509 Culver St", "3", fs);
    //Then
    List<Integer> actual = classUnderTest.getFireStation("1509 Culver St");
    assertEquals(List.of(1), actual);
  }

  @Test
  void upDateAddressNotExistToRepositoryNotValidate() {
    //Given
    FireStation fs = new FireStation();
    fs.setStation(3);
    fs.setAddress("NoWhere");
    //When
    classUnderTest.upDateFireStation("1509 Culver St", "3", fs);
    //Then
    List<Integer> actual = classUnderTest.getFireStation("NoWhere");
    assertTrue(actual.isEmpty());
  }

  @Test
  void removeFireStationToRepository() {
    //Given
    //When
    classUnderTest.removeFireStation("3", "1509 Culver St");
    //Then
    List<Integer> actual = classUnderTest.getFireStation("1509 Culver St");
    assertTrue(actual.isEmpty());
  }

  @Test
  void removeAllFireStationToRepository() {
    //Given
    //When
    classUnderTest.removeAllFireStations();
    //Then
    List<Integer> actual = classUnderTest.getAllFireStations();
    assertTrue(actual.isEmpty());
  }

}
