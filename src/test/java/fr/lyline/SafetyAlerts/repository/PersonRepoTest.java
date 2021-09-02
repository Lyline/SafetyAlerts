package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class PersonRepoTest {

  Person person = new Person();
  private PersonRepoImpl classUnderTest = new PersonRepoImpl();

  @BeforeEach
  void setUp() {
    classUnderTest = new PersonRepoImpl();
  }

  @Test
  void getAllPersonsTest() {
    //Given

    //When
    List result = classUnderTest.findAll();
    //Then
    assertEquals(23, result.size());
    assertEquals("class fr.lyline.SafetyAlerts.model.Person", result.get(1).getClass().toString());
  }

  @Test
  void getPersonByIdTest() {
    //Given

    //When
    Person result = classUnderTest.findById("JohnBoyd");
    //Then
    assertEquals("John", result.getFirstName());
    assertEquals("Boyd", result.getLastName());
    assertEquals("1509 Culver St", result.getAddress());
    assertEquals("Culver", result.getCity());
    assertEquals(97451, result.getZip());
    assertEquals("841-874-6512", result.getPhone());
    assertEquals("jaboyd@email.com", result.getEmail());
  }

  @Test
  void addPersonTest() {
    //Given
    person.setFirstName("Jean");
    person.setLastName("Bon");
    person.setAddress("666 HellRoad");
    person.setCity("HellCity");
    person.setZip(123456);
    person.setPhone("123-456-789");
    person.setEmail("jean@aol.com");

    //When
    classUnderTest.add(person);

    //Then
    Person result = classUnderTest.findById("JeanBon");
    assertEquals("Jean", result.getFirstName());
    assertEquals("Bon", result.getLastName());
    assertEquals("666 HellRoad", result.getAddress());
    assertEquals("HellCity", result.getCity());
    assertEquals(123456, result.getZip());
    assertEquals("123-456-789", result.getPhone());
    assertEquals("jean@aol.com", result.getEmail());
  }

  /*@Test
  void addAllPersonTest() {
    //Given
    person.setFirstName("Marcel");
    person.setLastName("Dugenou");

    Person person1 = new Person();
    person1.setFirstName("Jane");
    person1.setLastName("Doe");

    List<Person> list = new ArrayList<>();
    list.add(person);
    list.add(person1);

    //When
    classUnderTest.addAll(list);

    //Then
    List<Person> result = classUnderTest.findAll();
    assertTrue(result.contains(person));
    assertTrue(result.contains(person1));
  }*/

  @Test
  void upDatePersonAllInformationsTest() {
    //Given
    person = classUnderTest.findById("JohnBoyd");
    person.setAddress("new address");
    person.setZip(987654);
    person.setCity("new city");

    //When
    classUnderTest.update("JohnBoyd", person);

    //Then
    Person result = classUnderTest.findById("JohnBoyd");

    assertEquals("new address", result.getAddress());
    assertEquals("new city", result.getCity());
    assertEquals(987654, result.getZip());
    assertEquals("841-874-6512", result.getPhone());
    assertEquals("jaboyd@email.com", result.getEmail());
  }

  @Test
  void deletePersonTest() {
    //Given

    //When
    classUnderTest.deleteById("JohnBoyd");
    //Then
    assertEquals(null, classUnderTest.findById("JohnBoyd"));
  }

  /*@Test
  void deleteAllPersonTest() {
    //Given

    //When
    classUnderTest.deleteAll();
    //Then
    assertTrue(classUnderTest.findAll().isEmpty());
  }*/
}
