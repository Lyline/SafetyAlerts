package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.FireStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FireStationRepoTest {

  @Autowired
  private FireStationRepoImpl classUnderTest;

  @BeforeEach
  void setUp() {
    classUnderTest = new FireStationRepoImpl();
  }

  @Test
  void getAllAddressTest() {
    //Given

    //When
    List<FireStation> result = classUnderTest.findAll();

    //Then
    assertEquals(12, result.size());
  }

  @Test
  void getAddressListFireStationByIdTest() {
    //Given

    //When
    FireStation result = classUnderTest.findById("4-112 Steppes Pl");

    //Then
    assertEquals(4, result.getStation());
  }

  @Test
  void addFireStationTest() {
    //Given
    FireStation fireStationTest = new FireStation();
    fireStationTest.setStation(6);
    fireStationTest.setAddress("NoWhere");

    //When
    classUnderTest.add(fireStationTest);

    //Then
    List<FireStation> actual = classUnderTest.findAll();
    assertEquals(13, actual.size());
  }

  @Test
  void addAllFireStationTest() {
    //Given
    FireStation fireStation1 = new FireStation();
    fireStation1.setStation(1);
    fireStation1.setAddress("NoWhere");

    FireStation fireStation2 = new FireStation();
    fireStation2.setStation(3);
    fireStation2.setAddress("Gotham St");

    List<FireStation> list = new ArrayList<>();
    list.add(fireStation1);
    list.add(fireStation2);

    //When
    classUnderTest.addAll(list);

    //Then
    List<FireStation> actual = classUnderTest.findAll();
    assertEquals(14, actual.size());
  }

  @Test
  void upDateFireStationTest() {
    //Given
    FireStation fsToUpdate = new FireStation();
    fsToUpdate.setStation(2);
    fsToUpdate.setAddress("NoWhere");

    //When
    classUnderTest.update("1-947 E. Rose Dr", fsToUpdate);

    //Then
    assertEquals("NoWhere", classUnderTest.findById("2-NoWhere").getAddress());
  }

  @Test
  void deleteFireStationByIdTest() {
    //Given

    //When
    classUnderTest.deleteById("1-644 Gershwin Cir");

    //Then
    assertEquals(null, classUnderTest.findById("1-644 Gershwin Cir"));
  }

  @Test
  void deleteAllFireStationTest() {
    //Given

    //When
    classUnderTest.deleteAll();

    //Then
    assertTrue(classUnderTest.findAll().isEmpty());
  }
}
