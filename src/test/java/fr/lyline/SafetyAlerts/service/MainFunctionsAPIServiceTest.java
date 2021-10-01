package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.ObjectMapper.ChildAlert;
import fr.lyline.SafetyAlerts.ObjectMapper.PersonInfo;
import fr.lyline.SafetyAlerts.model.FireStation;
import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.model.Person;
import fr.lyline.SafetyAlerts.repository.FireStationRepoImpl;
import fr.lyline.SafetyAlerts.repository.MedicalRecordRepoImpl;
import fr.lyline.SafetyAlerts.repository.PersonRepoImpl;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class MainFunctionsAPIServiceTest {
  PersonRepoImpl personRepo = mock(PersonRepoImpl.class);
  FireStationRepoImpl fireStationRepo = mock(FireStationRepoImpl.class);
  MedicalRecordRepoImpl medicalRecordRepo = mock(MedicalRecordRepoImpl.class);
  PersonInfo personInfo = mock(PersonInfo.class);

  MainFunctionsAPIService classUnderTest = new MainFunctionsAPIService(personRepo, fireStationRepo, medicalRecordRepo);

  List<Person> simpsonFamily = new ArrayList<>();
  List<MedicalRecord> medicalRecords = new ArrayList<>();
  List<FireStation> fireStations = new ArrayList<>();

  @BeforeEach
  void setUp() {
    simpsonFamily.add(new Person("Homer", "Simpson", "742 Evergreen Terrace",
        "Springfield", 80085, "123-456", "homer@test.com"));
    simpsonFamily.add(new Person("Marge", "Simpson", "742 Evergreen Terrace",
        "Springfield", 80085, "123-456", "marge@test.com"));
    simpsonFamily.add(new Person("Bart", "Simpson", "742 Evergreen Terrace",
        "Springfield", 80085, "123-456", "bart@test.com"));

    medicalRecords.add(new MedicalRecord("Homer", "Simpson", new DateTime("1956-05-12"),
        new String[]{"duff 250cl"}, new String[]{"work"}));
    medicalRecords.add(new MedicalRecord("Marge", "Simpson", new DateTime("1957-03-19"),
        new String[]{}, new String[]{}));
    medicalRecords.add(new MedicalRecord("Bart", "Simpson", new DateTime("2010-02-23"),
        new String[]{}, new String[]{"school", "vegetables"}));

    fireStations.add(new FireStation(1, "742 Evergreen Terrace"));
  }

  @Test
  void shouldGetCommunityEmailReturnThreeEmails() {
    //Given
    //When
    when(personRepo.findAll()).thenReturn(simpsonFamily);

    //Then
    assertEquals(3, classUnderTest.getCommunityEmail("Springfield").size());
    assertThat(classUnderTest.getCommunityEmail("Springfield"),
        contains("bart@test.com", "marge@test.com", "homer@test.com"));
  }

  @Test
  void shouldGetCommunityEmailReturnAnEmptyList() {
    //Given
    //When
    when(personRepo.findAll()).thenReturn(new ArrayList<>());
    //Then
    assertTrue(classUnderTest.getCommunityEmail("SpringField").isEmpty());
  }

  @Test
  void shouldGetPhoneAlertReturnOnePhoneNumber() {
    //Given
    //When
    when(personRepo.findAll()).thenReturn(simpsonFamily);
    when(fireStationRepo.findAll()).thenReturn(fireStations);

    //Then
    assertEquals(1, classUnderTest.getPhoneForAlert(1).size());
    assertEquals("[123-456]", classUnderTest.getPhoneForAlert(1).toString());
  }

  @Test
  void shouldGetPhoneAlertReturnAnEmptyList() {
    //Given
    when(personRepo.findAll()).thenReturn(new ArrayList<>());
    when(fireStationRepo.findAll()).thenReturn(fireStations);
    //When
    //Then
    assertTrue(classUnderTest.getPhoneForAlert(1).isEmpty());
  }

  @Test
  void shouldGetPersonInfo() {
    //Given
    when(personRepo.findById("Bart", "Simpson")).thenReturn(simpsonFamily.get(2));
    when(medicalRecordRepo.findByFirstNameAndLastName("Bart", "Simpson")).thenReturn(medicalRecords.get(2));

    //When
    PersonInfo result = classUnderTest.getPersonInfo("Bart", "Simpson");

    //Then
    assertEquals("Bart", result.getFirstName());
    assertEquals("Simpson", result.getLastName());
    assertEquals(11, result.getAge());
    assertArrayEquals(new String[]{}, result.getMedications());
    assertArrayEquals(new String[]{"school", "vegetables"}, result.getAllergies());
    assertEquals("bart@test.com", result.getEmail());
  }

  @Test
  void shouldGetPersonInfoReturnNull() {
    //Given
    //When
    when(personRepo.findById("John", "Doe")).thenReturn(new Person());
    when(medicalRecordRepo.findByFirstNameAndLastName("John", "Doe")).thenReturn(new MedicalRecord());
    //Then
    PersonInfo result = classUnderTest.getPersonInfo("John", "Doe");
    assertNull(result);
  }

  @Test
  void shouldGetChildAlertReturnOneChildWithTwoParents() {
    //Given
    when(personRepo.findAll()).thenReturn(simpsonFamily);
    when(medicalRecordRepo.findByFirstNameAndLastName("Homer", "Simpson")).thenReturn(medicalRecords.get(0));
    when(medicalRecordRepo.findByFirstNameAndLastName("Marge", "Simpson")).thenReturn(medicalRecords.get(1));
    when(medicalRecordRepo.findByFirstNameAndLastName("Bart", "Simpson")).thenReturn(medicalRecords.get(2));

    //When
    List<ChildAlert> actual = classUnderTest.getChildAlert("742 Evergreen Terrace");

    //Then
    assertEquals(1, actual.size());
    assertEquals("Bart", actual.get(0).getChildFirstName());
    assertEquals("Simpson", actual.get(0).getChildLastName());
    assertEquals("11", actual.get(0).getAge());
    assertEquals("[{parentFirstName=Homer, parentLastName=Simpson}, {parentFirstName=Marge, parentLastName=Simpson}]",
        actual.get(0).getParents().toString());
  }

  @Test
  void shouldGetChildAlertReturnAnEmptyList() {
    //Given
    when(personRepo.findAll()).thenReturn(new ArrayList<>());
    //When
    List<ChildAlert> actual = classUnderTest.getChildAlert("742 Evergreen Terrace");
    //Then
    assertTrue(actual.isEmpty());
  }

  @Test
  void getResidentsByStationListWithOnePerson() {
    //Given
    Person person = new Person("Homer", "Simpson", "742 Evergreen Terrace",
        "Springfield", 80085, "123-456", "homer@test.com");
    MedicalRecord medic = new MedicalRecord("Homer", "Simpson", new DateTime("1956-05-12"),
        new String[]{"duff 250cl"}, new String[]{"work"});
    PersonInfo personInfoMock = new PersonInfo("Homer", "Simpson",
        "123-456", 65, new String[]{"duff 250cl"}, new String[]{"work"});

    when(personRepo.findAll()).thenReturn(List.of(person));
    when(fireStationRepo.findAll()).thenReturn(fireStations);
    when(medicalRecordRepo.findByFirstNameAndLastName("Homer", "Simpson")).thenReturn(medic);

    when(personInfo.add(person, medic)).thenReturn(personInfoMock);

    //When
    Map<Integer, List<Map<String, List<PersonInfo>>>> actual = classUnderTest.getResidentsContactFromToFireStation(new int[]{1});

    //Then
    assertEquals(1, actual.size());
    assertEquals("Homer", actual.get(1).get(0).get("742 Evergreen Terrace").get(0).getFirstName());
    assertEquals("Simpson", actual.get(1).get(0).get("742 Evergreen Terrace").get(0).getLastName());
    assertEquals("homer@test.com", actual.get(1).get(0).get("742 Evergreen Terrace").get(0).getEmail());
    assertEquals("123-456", actual.get(1).get(0).get("742 Evergreen Terrace").get(0).getPhone());
    assertEquals(65, actual.get(1).get(0).get("742 Evergreen Terrace").get(0).getAge());
    assertArrayEquals(new String[]{"duff 250cl"},
        actual.get(1).get(0).get("742 Evergreen Terrace").get(0).getMedications());
    assertArrayEquals(new String[]{"work"},
        actual.get(1).get(0).get("742 Evergreen Terrace").get(0).getAllergies());
  }

  @Test
  void getResidentsByStationListWithoutPerson() {
    //Given
    when(personRepo.findAll()).thenReturn(List.of());
    when(fireStationRepo.findAll()).thenReturn(fireStations);
    when(medicalRecordRepo.findByFirstNameAndLastName("Homer", "Simpson")).thenReturn(null);

    //When
    Map<Integer, List<Map<String, List<PersonInfo>>>> actual = classUnderTest.getResidentsContactFromToFireStation(new int[]{1});

    //Then
    assertTrue(actual.isEmpty());
  }

  @Test
  void getPersonsInfoByStationWithTwoAdultsAndOneChildReturnAllInformations() {
    //Given
    when(personRepo.findAll()).thenReturn(simpsonFamily);
    when(fireStationRepo.findAll()).thenReturn(fireStations);
    when(medicalRecordRepo.findByFirstNameAndLastName("Homer", "Simpson")).thenReturn(medicalRecords.get(0));
    when(medicalRecordRepo.findByFirstNameAndLastName("Marge", "Simpson")).thenReturn(medicalRecords.get(1));
    when(medicalRecordRepo.findByFirstNameAndLastName("Bart", "Simpson")).thenReturn(medicalRecords.get(2));

    //When
    Map<String, List<Map<String, String>>> actual = classUnderTest.getPersonsInfoByStation(1);

    //Then
    assertEquals("Homer", actual.get("persons").get(0).get("firstName"));
    assertEquals("Marge", actual.get("persons").get(1).get("firstName"));
    assertEquals("Bart", actual.get("persons").get(2).get("firstName"));
    assertEquals("1", actual.get("count").get(0).get("children"));
    assertEquals("2", actual.get("count").get(0).get("adults"));
  }


  @Test
  void getPersonsInfoByStationWithTwoAdultsAndOneChildReturnAnEmptyMap() {
    //Given
    when(personRepo.findAll()).thenReturn(List.of());
    when(fireStationRepo.findAll()).thenReturn(fireStations);
    when(medicalRecordRepo.findByFirstNameAndLastName("Homer", "Simpson")).thenReturn(null);
    when(medicalRecordRepo.findByFirstNameAndLastName("Marge", "Simpson")).thenReturn(null);
    when(medicalRecordRepo.findByFirstNameAndLastName("Bart", "Simpson")).thenReturn(null);

    //When
    Map<String, List<Map<String, String>>> actual = classUnderTest.getPersonsInfoByStation(1);

    //Then
    assertTrue(actual.isEmpty());
  }

  @Test
  void getPersonsInfoByAddressWithThreePersonsReturnPersonalInformationsAndStationNumber() {
    //Given
    when(personRepo.findAll()).thenReturn(simpsonFamily);
    when(fireStationRepo.findAll()).thenReturn(fireStations);
    when(medicalRecordRepo.findByFirstNameAndLastName("Homer", "Simpson")).thenReturn(medicalRecords.get(0));
    when(medicalRecordRepo.findByFirstNameAndLastName("Marge", "Simpson")).thenReturn(medicalRecords.get(1));
    when(medicalRecordRepo.findByFirstNameAndLastName("Bart", "Simpson")).thenReturn(medicalRecords.get(2));

    //When
    Map<String, List<Object>> actual = classUnderTest.getPersonsInfoByAddress("742 Evergreen Terrace");

    //Then
    assertEquals("{firstName=Homer, " +
        "lastName=Simpson, " +
        "information={" +
        "allergies=[work], " +
        "phone=123-456, " +
        "medications=[duff 250cl], " +
        "age=65}}", actual.get("persons").get(0).toString());
    assertEquals("{firstName=Marge, " +
        "lastName=Simpson, " +
        "information={" +
        "allergies=[], " +
        "phone=123-456, " +
        "medications=[], " +
        "age=64}}", actual.get("persons").get(1).toString());
    assertEquals("{firstName=Bart, " +
        "lastName=Simpson, " +
        "information={" +
        "allergies=[school, vegetables], " +
        "phone=123-456, " +
        "medications=[], " +
        "age=11}}", actual.get("persons").get(2).toString());
    assertEquals("[1]", actual.get("firestation").get(0).toString());
  }

  @Test
  void getPersonsInfoByAddressWithNobodyReturnAnEmptyMap() {
    //Given
    when(personRepo.findAll()).thenReturn(List.of());
    when(fireStationRepo.findAll()).thenReturn(List.of());
    when(medicalRecordRepo.findByFirstNameAndLastName("Homer", "Simpson")).thenReturn(null);
    when(medicalRecordRepo.findByFirstNameAndLastName("Marge", "Simpson")).thenReturn(null);
    when(medicalRecordRepo.findByFirstNameAndLastName("Bart", "Simpson")).thenReturn(null);

    //When
    Map<String, List<Object>> actual = classUnderTest.getPersonsInfoByAddress("742 Evergreen Terrace");

    //Then
    assertTrue(actual.isEmpty());
  }
}