package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.FireStation;
import fr.lyline.SafetyAlerts.utils.JsonConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FireStationRepoTest {
  private final JsonConverter data = mock(JsonConverter.class);
  private final FireStationRepoImpl classUnderTest = new FireStationRepoImpl(data);
  private final List<FireStation> stationList = new ArrayList<>();
  String fileJsonPath = "src/main/resources/fireStation.json";
  FireStation station1 = new FireStation(1, "742 Evergreen Terrace");
  FireStation station2 = new FireStation(2, "42 Wallaby Way");
  FireStation station3 = new FireStation(3, "42 Wallaby Way");

  @BeforeEach
  void setUp() {
    stationList.add(station1);
    stationList.add(station2);
    stationList.add(station3);
  }

  @Test
  void whenGetAllStationsReturnTreeStations() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(stationList);

    //When
    List<FireStation> actual = classUnderTest.findAll();

    //Then
    assertEquals(3, actual.size());
  }

  @Test
  void whenGetAllStationsReturnReturnAnEmptyList() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(List.of());

    //When
    List<FireStation> actual = classUnderTest.findAll();

    //Then
    assertTrue(actual.isEmpty());
  }

  @Test
  void whenGetExistingPersonReturnThisPerson() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(stationList);

    //When
    FireStation actual = classUnderTest.findByStationNumberAndAddress(1, "742 Evergreen Terrace");

    //Then
    assertSame(station1, actual);
  }

  @Test
  void whenGetNotExistingPersonReturnNull() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(stationList);

    //When
    FireStation actual = classUnderTest.findByStationNumberAndAddress(1, "NoWhere");

    //Then
    assertNull(actual);
  }

  @Test
  void whenAddNewStationReturnTrueAndAddNewStation() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(stationList);
    FireStation station = new FireStation(1, "221B Baker Street");

    //When
    boolean result = classUnderTest.add(station);

    //Then
    FireStation actual = classUnderTest.findByStationNumberAndAddress(1, "221B Baker Street");
    assertTrue(result);
    assertSame(station, actual);
  }

  @Test
  void whenAddNewIncompleteStationReturnFalse() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(stationList);
    FireStation station = new FireStation();
    station.setAddress("221B Baker Street");

    //When
    boolean result = classUnderTest.add(station);

    //Then
    FireStation actual = classUnderTest.findByStationNumberAndAddress(1, "221B Baker Street");
    assertFalse(result);
    assertNotSame(station, actual);
    assertNull(actual);
  }

  @Test
  void whenAddNewExistingStationReturnFalse() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(stationList);

    //When
    boolean result = classUnderTest.add(station1);

    //Then
    FireStation actual = classUnderTest.findByStationNumberAndAddress(1, "742 Evergreen Terrace");
    assertFalse(result);
    assertNotNull(actual);
  }

  @Test
  void whenUpdateExistingStationReturnTrue() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(stationList);
    FireStation fireStationToUpdate = new FireStation(1, "221B Baker Street");

    //When
    boolean actual = classUnderTest.update(station3.getStation(), station3.getAddress(), fireStationToUpdate);

    //Then
    assertTrue(actual);
  }

  @Test
  void whenUpdateExistingStationByAnAnotherExistingStationReturnTrue() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(stationList);
    FireStation fireStationToUpdate = new FireStation(3, "42 Wallaby Way");

    //When
    boolean result = classUnderTest.update(station1.getStation(), station1.getAddress(), fireStationToUpdate);

    //Then
    assertTrue(result);
  }

  @Test
  void whenUpdateNotExistingStationReturnFalse() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(stationList);
    FireStation fireStationNotExist = new FireStation(1, "NoWhere");

    //When
    boolean result = classUnderTest.update(fireStationNotExist.getStation(), fireStationNotExist.getAddress(), station1);

    //Then
    assertFalse(result);
  }

  @Test
  void whenDeleteExistingStationReturnTrue() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(stationList);
    //When
    boolean actual = classUnderTest.deleteByStationAndAddress(2, "42 Wallaby Way");
    //Then
    assertTrue(actual);
  }

  @Test
  void whenDeleteNotExistingStationReturnFalse() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(stationList);
    //When
    boolean actual = classUnderTest.deleteByStationAndAddress(12, "42 Wallaby Way");
    //Then
    assertFalse(actual);
  }
}