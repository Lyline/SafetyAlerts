package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.Person;
import fr.lyline.SafetyAlerts.repository.PersonRepoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersonServiceTest {
  @Autowired
  PersonService classUnderTest;

  @Autowired
  PersonRepoImpl repository;

  @BeforeEach
  void setUp() {
    repository = new PersonRepoImpl();
    classUnderTest = new PersonServiceImpl(repository);
  }

  @Test
  void getPersonByIdValidate() {
    //Given
    //When
    Person actual = classUnderTest.getPerson("JohnBoyd");
    //Then
    assertEquals("John", actual.getFirstName());
    assertEquals("Boyd", actual.getLastName());
  }

  @Test
  void getPersonByIdNotValidate() {
    //Given
    //When
    Person actual = classUnderTest.getPerson("JohnDoe");
    //Then
    assertNull(actual);
  }

  @Test
  void getAllPersonValidate() {
    //Given
    //When
    List<Person> actual = classUnderTest.getAllPersons();
    //Then
    assertEquals(23, actual.size());
  }

  @Test
  void getAllPersonReturnEmptyList() {
    //Given
    repository.deleteAll();
    //When
    List<Person> actual = classUnderTest.getAllPersons();
    //Then
    assertTrue(actual.isEmpty());
  }

  @Test
  void addPersonToTheRepositoryValidate() {
    //Given
    Person person = new Person();
    person.setFirstName("John");
    person.setLastName("Doe");
    person.setAddress("NoWhere");
    person.setCity("New City");
    person.setZip(123456);
    person.setPhone("123-456");
    person.setEmail("john@aol.com");

    //When
    classUnderTest.addPerson(person);

    //Then
    Person actual = classUnderTest.getPerson("JohnDoe");
    assertEquals(person, actual);
  }

  @Test
  void addPersonToRepositoryNotValidate() {
    //Given
    Person person = new Person();
    person.setFirstName("John");
    person.setLastName("Doe");
    //When
    classUnderTest.addPerson(person);
    //Then
    Person actual = classUnderTest.getPerson("JohnDoe");
    assertNotEquals(person, actual);
  }

  /*@Test
  void addTwoValidePersonToRepository() {
    //Given
    Person person1 = new Person();
    person1.setFirstName("John");
    person1.setLastName("Doe");
    person1.setAddress("NoWhere");
    person1.setCity("New City");
    person1.setZip(123456);
    person1.setPhone("123-456");
    person1.setEmail("john@aol.com");

    Person person2 = new Person();
    person2.setFirstName("Jane");
    person2.setLastName("Doe");
    person2.setAddress("NoWhere");
    person2.setCity("New City");
    person2.setZip(123456);
    person2.setPhone("123-456");
    person2.setEmail("jane@aol.com");

    List<Person> list = new ArrayList<>();
    list.add(person1);
    list.add(person2);
    //When
    classUnderTest.addAllPersons(list);
    //Then
    Person actual1 = classUnderTest.getPerson("JohnDoe");
    Person actual2 = classUnderTest.getPerson("JaneDoe");
    assertEquals(person1, actual1);
    assertEquals(person2, actual2);
  }*/

  /*@Test
  void addOneValidePersonAndOneInvalidePersonReturnOnlyValidePerson() {
    //Given
    Person person1 = new Person();
    person1.setFirstName("John");
    person1.setLastName("Doe");
    person1.setAddress("NoWhere");
    person1.setCity("New City");
    person1.setZip(123456);
    person1.setPhone("123-456");
    person1.setEmail("john@aol.com");

    Person person2 = new Person();
    person2.setFirstName("Jane");
    person2.setLastName("Doe");

    List<Person> list = new ArrayList<>();
    list.add(person1);
    list.add(person2);
    //When
    classUnderTest.addAllPersons(list);
    //Then
    Person actual1 = classUnderTest.getPerson("JohnDoe");
    Person actual2 = classUnderTest.getPerson("JaneDoe");
    assertEquals(person1, actual1);
    assertNull(actual2);
  }*/

  @Test
  void upDatePersonToRepositoryValide() {
    //Given
    Person personToUpDate = classUnderTest.getPerson("JohnBoyd");
    personToUpDate.setAddress("New Address");
    personToUpDate.setPhone("000-000");
    //When
    classUnderTest.upDatePerson("JohnBoyd", personToUpDate);
    //Then
    Person actual = classUnderTest.getPerson("JohnBoyd");
    assertEquals(personToUpDate, actual);
  }

  @Test
  void upDatePersonNotExistToRepositoryNotValidate() {
    //Given
    Person person = new Person();
    person.setFirstName("John");
    person.setLastName("Doe");
    person.setAddress("NoWhere");
    person.setCity("New City");
    person.setZip(123456);
    person.setPhone("123-456");
    person.setEmail("john@aol.com");
    //When
    classUnderTest.upDatePerson("JohnDoe", person);
    //Then
    Person actual = classUnderTest.getPerson("JohnDoe");
    assertNull(actual);
  }

  @Test
  void removePersonToRepository() {
    //Given
    //When
    classUnderTest.removePerson("JohnBoyd");
    //Then
    Person actual = classUnderTest.getPerson("JohnBoyd");
    assertNull(actual);
  }

  /*@Test
  void removeAllPersonsToRepository() {
    //Given
    //When
    classUnderTest.removeAllPersons();
    //Then
    List<Person> actual = classUnderTest.getAllPersons();
    assertTrue(actual.isEmpty());
  }*/
}
