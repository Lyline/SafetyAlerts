package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.Person;
import fr.lyline.SafetyAlerts.repository.PersonRepoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonServiceTest {
  private final List<Person> personList = new ArrayList<>();
  PersonRepoImpl repository = mock(PersonRepoImpl.class);
  PersonServiceImpl classUnderTest = new PersonServiceImpl(repository);
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
  void whenGetExistingPersonReturnThisPerson() {
    //Given
    when(repository.findById("Homer", "Simpson")).thenReturn(personList.get(0));

    //When
    Person actual = classUnderTest.getPerson("Homer", "Simpson");

    //Then
    assertSame(person1, actual);
  }

  @Test
  void whenGetNotExistingPersonReturnNull() {
    //Given
    when(repository.findById("John", "Doe")).thenReturn(null);

    //When
    Person actual = classUnderTest.getPerson("John", "Doe");

    //Then
    assertNull(actual);
  }

  @Test
  void whenGetAllPersonsReturnThreePersons() {
    //Given
    when(repository.findAll()).thenReturn(personList);

    //When
    List<Person> actual = classUnderTest.getAllPersons();

    //Then
    assertEquals(3, actual.size());
    assertEquals("Homer", actual.get(0).getFirstName());
    assertEquals("Marge", actual.get(1).getFirstName());
    assertEquals("Bart", actual.get(2).getFirstName());
  }

  @Test
  void whenGetAllPersonsNoExitingReturnAnEmptyList() {
    //Given
    when(repository.findAll()).thenReturn(List.of());

    //When
    List<Person> actual = classUnderTest.getAllPersons();

    //Then
    assertTrue(actual.isEmpty());
  }

  @Test
  void whenAddNewValidatePersonReturnTrue() {
    //Given

    Person person = new Person("John", "Doe", "Address",
        "City", 1, "123-456", "john@test.com");

    when(repository.add(person)).thenReturn(true);
    //When
    boolean actual = classUnderTest.addPerson(person);

    //Then
    assertTrue(actual);
  }

  @Test
  void whenAddNewNotValidatePersonReturnFalse() {
    //Given
    when(repository.findAll()).thenReturn(personList);

    Person person = new Person("John", "Doe", "",
        "", 1, "", "");

    //When
    boolean actual = classUnderTest.addPerson(person);

    //Then
    assertFalse(actual);
  }

  @Test
  void whenUpdateExistingPersonReturnTrue() {
    //Given
    when(repository.findById("Homer", "Simpson")).thenReturn(personList.get(0));
    Person personToUpdate = new Person("Homer", "Simpson",
        "newAddress", "NewCity", 1, "123", "homer@sprinfield.com");
    //When
    boolean actual = classUnderTest.upDatePerson(personToUpdate);

    //Then
    assertTrue(actual);
  }

  @Test
  void whenUpdateNotExistingPersonReturnFalse() {
    //Given
    when(repository.findById("Homer", "Simpson")).thenReturn(personList.get(0));
    Person personToUpdate = new Person("Maggie", "Simpson",
        "newAddress", "NewCity", 1, "123", "homer@test.com");
    //When
    boolean actual = classUnderTest.upDatePerson(personToUpdate);

    //Then
    assertFalse(actual);
  }

  @Test
  void whenDeleteExistingPersonReturnTrue() {
    //Given
    when(repository.findById("Homer", "Simpson")).thenReturn(personList.get(0));

    //When
    boolean actual = classUnderTest.removePerson("Homer", "Simpson");

    //Then
    assertTrue(actual);
  }

  @Test
  void whenDeleteNotExistingPersonReturnTrue() {
    //Given
    when(repository.findById("John", "Doe")).thenReturn(null);

    //When
    boolean actual = classUnderTest.removePerson("John", "Doe");

    //Then
    assertFalse(actual);
  }
}
