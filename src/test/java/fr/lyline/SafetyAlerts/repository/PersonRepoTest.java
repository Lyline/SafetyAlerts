package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.Person;
import fr.lyline.SafetyAlerts.utils.JsonConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonRepoTest {

  private final JsonConverter data = mock(JsonConverter.class);
  private final PersonRepoImpl classUnderTest = new PersonRepoImpl(data);
  private final List<Person> personList = new ArrayList<>();
  String fileJsonPath = "src/main/resources/person.json";
  Person person1 = new Person("Homer", "Simpson", "742 Evergreen Terrace",
      "Springfield", 80085, "123-456", "homer@test.com");
  Person person2 = new Person("Marge", "Simpson", "742 Evergreen Terrace",
      "Springfield", 80085, "123-456", "homer@test.com");
  Person person3 = new Person("Bart", "Simpson", "742 Evergreen Terrace",
      "Springfield", 80085, "123-456", "homer@test.com");

  @BeforeEach
  void setUp() {
    personList.add(person1);
    personList.add(person2);
    personList.add(person3);
  }

  @Test
  void whenGetAllPersonsTestReturnThreePersons() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(personList);

    //When
    List<Person> actual = classUnderTest.findAll();

    //Then
    assertEquals(3, actual.size());

  }

  @Test
  void whenGetAllPersonsTestReturnAnEmptyList() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(List.of());

    //When
    List<Person> actual = classUnderTest.findAll();

    //Then
    assertTrue(actual.isEmpty());

  }

  @Test
  void whenGetPersonByIdTestReturnTheGoodPerson() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(personList);

    //When
    Person actual = classUnderTest.findById("Bart", "Simpson");

    //Then
    assertSame(person3, actual);
  }

  @Test
  void whenGetPersonByIdTestReturnNull() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(personList);

    //When
    Person actual = classUnderTest.findById("John", "Doe");

    //Then
    assertNull(actual);
  }

  @Test
  void whenAddPersonReturnTrueAndAddNewPerson() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(personList);
    Person person = new Person("John", "Doe", "newAddress",
        "NewCity", 1, "123-456", "john@test.com");
    //When
    boolean result = classUnderTest.add(person);

    //Then
    Person actual = classUnderTest.findById("John", "Doe");
    assertTrue(result);
    assertSame(person, actual);
  }

  @Test
  void whenAddExistingPersonReturnFalse() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(personList);

    //When
    boolean result = classUnderTest.add(person1);

    //Then
    Person actual = classUnderTest.findById("Homer", "Simpson");
    assertFalse(result);
    assertSame(person1, actual);
  }


  @Test
  void whenUpDateExistingPersonReturnTrue() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(personList);
    Person personToUpdate = new Person("Homer", "Simpson", "NoAddress",
        "NoCity", 1, "123-456", "");

    //When
    boolean result = classUnderTest.update(personToUpdate);

    //Then
    Person actual = classUnderTest.findById("Homer", "Simpson");
    assertTrue(result);
    assertSame(personToUpdate, actual);
  }

  @Test
  void whenUpDatePersonNotExistingReturnFalse() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(personList);
    Person personToUpdate = new Person("John", "Doe", "NoAddress",
        "NoCity", 1, "123-456", "");

    //When
    boolean result = classUnderTest.update(personToUpdate);

    //Then
    Person actual = classUnderTest.findById("John", "Doe");
    assertFalse(result);
    assertNull(actual);
  }

  @Test
  void whenDeleteExistingPersonReturnTrue() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(personList);
    int personsCountBeforeDelete = personList.size();

    //When
    boolean result = classUnderTest.deleteByFirstNameAndLastName("Homer", "Simpson");
    int personsCountAfterDelete = personList.size();

    //Then
    assertEquals(3, personsCountBeforeDelete);
    assertTrue(result);
    assertEquals(2, personsCountAfterDelete);
    assertNull(classUnderTest.findById("Homer", "Simpson"));
  }

  @Test
  void whenDeleteNotExistingPersonReturnFalse() {
    //Given
    when(data.convertJsonToObject(fileJsonPath)).thenReturn(personList);

    //When
    boolean result = classUnderTest.deleteByFirstNameAndLastName("John", "Doe");

    //Then
    assertFalse(result);
  }
}
