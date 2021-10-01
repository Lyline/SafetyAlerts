package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.FireStation;
import fr.lyline.SafetyAlerts.repository.FireStationRepoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class FireStationServiceTest {

  private final List<FireStation> stationList = new ArrayList<>();
  FireStationRepoImpl repository = mock(FireStationRepoImpl.class);
  FireStationServiceImpl classUnderTest = new FireStationServiceImpl(repository);
  FireStation station1 = new FireStation(1, "742 Evergreen Terrace");
  FireStation station2 = new FireStation(1, "42 Wallaby Way");
  FireStation station3 = new FireStation(2, "42 Wallaby Way");

  @BeforeEach
  void setUp() {
    stationList.add(station1);
    stationList.add(station2);
    stationList.add(station3);
  }

  @Test
  void getFireStationByValidAddressReturnTwoResults() {
    //Given
    when(repository.findAll()).thenReturn(stationList);
    //When
    List<Integer> actual = classUnderTest.getFireStation("42 Wallaby Way");
    //Then
    assertEquals(Arrays.asList(1, 2), actual);
  }

  @Test
  void getFireStationNotValideAddressReturnNull() {
    //Given
    when(repository.findAll()).thenReturn(List.of());

    //When
    List<Integer> actual = classUnderTest.getFireStation("NoWhere");

    //Then
    assertTrue(actual.isEmpty());
  }

  @Test
  void getAllFireStationsReturnTwoResults() {
    //Given
    when(repository.findAll()).thenReturn(stationList);
    //When
    List<Integer> actual = classUnderTest.getAllFireStations();
    //Then
    assertEquals(List.of(1, 2), actual);
  }

  @Test
  void getAllFireStationsReturnEmptyList() {
    //Given
    when(repository.findAll()).thenReturn(List.of());
    //When
    List<Integer> actual = classUnderTest.getAllFireStations();
    //Then
    assertTrue(actual.isEmpty());
  }

  @Test
  void whenAddNewValidateAddressWithFireStationReturnTrue() {
    //Given
    FireStation fireStationAdd = new FireStation(2, "NoWhere");
    when(repository.add(fireStationAdd)).thenReturn(true);

    //When
    boolean actual = classUnderTest.addFireStation(fireStationAdd);

    //Then
    assertTrue(actual);
  }

  @Test
  void whenAddNewExistingAddressWithFireStationReturnFalse() {
    //Given
    FireStation fireStationAdd = new FireStation(2, "42 Wallaby Way");

    //When
    boolean actual = classUnderTest.addFireStation(fireStationAdd);

    //Then
    assertFalse(actual);
  }

  @Test
  void whenUpdateValideAddressFireStationReturnTrue() {
    //Given
    FireStation fireStationToUpdate = new FireStation(5, "42 Wallaby Way");
    when(repository.update(station3.getStation(), station3.getAddress(), fireStationToUpdate)).thenReturn(true);

    //When
    boolean actual = classUnderTest.updateFireStation(station3.getStation(), station3.getAddress(), fireStationToUpdate);

    //Then
    assertTrue(actual);
  }

  @Test
  void whenUpdateAddressNotExistReturnFalse() {
    //Given
    FireStation fireStationNotExist = new FireStation();
    FireStation fireStationToUpdate = new FireStation(5, "42 Wallaby Way");
    when(repository.update(fireStationNotExist.getStation(), fireStationNotExist.getAddress(), fireStationToUpdate)).thenReturn(false);

    //When
    boolean actual = classUnderTest.updateFireStation(fireStationNotExist.getStation(), fireStationNotExist.getAddress(), fireStationToUpdate);
    //Then
    assertFalse(actual);
  }

  @Test
  void whenRemoveExistingFireStationReturnTrue() {
    //Given
    when(repository.deleteByStationAndAddress(2, "42 Wallaby Way")).thenReturn(true);

    //When
    boolean actual = classUnderTest.removeFireStation("2", "42 Wallaby Way");

    //Then
    assertTrue(actual);
  }

  @Test
  void whenRemoveNotExistingFireStationReturnFalse() {
    //Given
    when(repository.deleteByStationAndAddress(2, "NoWhere")).thenReturn(false);

    //When
    boolean actual = classUnderTest.removeFireStation("2", "NoWhere");

    //Then
    assertFalse(actual);
  }
}
