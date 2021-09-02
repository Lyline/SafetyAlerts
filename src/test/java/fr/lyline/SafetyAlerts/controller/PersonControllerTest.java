package fr.lyline.SafetyAlerts.controller;

import fr.lyline.SafetyAlerts.model.Person;
import fr.lyline.SafetyAlerts.repository.PersonRepoImpl;
import fr.lyline.SafetyAlerts.service.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PersonController.class)
@AutoConfigureMockMvc
public class PersonControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PersonServiceImpl service;

  @MockBean
  private PersonRepoImpl repository;


  /*@BeforeEach
  void setUp() {
    service = new PersonServiceImpl(repository);
  }*/

  @Test
  void getPersonsReturnStatus200() throws Exception {
    //Given
    List<Person> personList = new ArrayList<>();

    Person newPerson1 = new Person("John", "Doe", "address",
        "City", 2, "123456", "j@aol.com");

    personList.add(newPerson1);
    when(service.getAllPersons()).thenReturn(personList);

    //When
    this.mockMvc.perform(get("/persons"))
        .andExpect(status().is2xxSuccessful());
    //Then
  }

  @Test
  void getPersonReturnStatus200() throws Exception {
    //Given
    List<Person> personList = new ArrayList<>();

    Person newPerson1 = new Person("Roger", "Boyd", "address",
        "City", 2, "123456", "j@aol.com");

    personList.add(newPerson1);
    when(service.getPerson("Roger_Boyd")).thenReturn(newPerson1);

    //When
    mockMvc.perform(get("/persons/Roger_Boyd"))
        .andExpect(status().isOk());
    //Then
  }

  @Test
  void getPersonReturnStatus400() throws Exception {
    //Given
    //When
    mockMvc.perform(get("/person/John_Doe"))
        .andExpect(status().isNotFound());
    //Then
  }

  @Test
  void addPersonReturnStatus200() throws Exception {
    //Given
    Person personMock = new Person();
    personMock.setFirstName("Johnny");
    personMock.setLastName("Doe1");
    personMock.setAddress("New Address");
    personMock.setCity("New City");
    personMock.setZip(1234);
    personMock.setPhone("123-345");
    personMock.setEmail("john@aol.com");

    when(service.addPerson(personMock)).thenReturn(personMock);

    //When
   /* mockMvc.perform(post("/person")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
        .andExpect(status().isCreated()));*/


    //Then
  }

  @Test
  void upDatePersonReturnStatus200() throws Exception {
    //Given
    //When
    mockMvc.perform(put("/person/John_Boyd"))
        .andExpect(status().isOk());
    //Then
  }

  @Test
  void removePersonReturnStatus200() throws Exception {
    //Given
    //When
    mockMvc.perform(delete("/person/John_Boyd"))
        .andExpect(status().isOk());
    //Then
  }

  @Test
  void addPersonReturnStatus201() throws Exception {
    //Given
    Person personTest = new Person();
    personTest.setFirstName("A");
    personTest.setLastName("X");
    personTest.setAddress("address");
    personTest.setCity("t");
    personTest.setZip(123);
    personTest.setPhone("123456");
    personTest.setEmail("klm");

    Person personTest1 = new Person();
    personTest1.setFirstName("Arthur");
    personTest1.setLastName("X");
    personTest1.setAddress("address");
    personTest1.setCity("t");
    personTest1.setZip(123);
    personTest1.setPhone("123456");
    personTest1.setEmail("klm");


    String json = "{ \"firstName\":\"Arthur\", " +
        "\"lastName\":\"X\", " +
        "\"address\":\"address\", " +
        "\"city\":\"t\", " +
        "\"zip\":\"123\", " +
        "\"phone\":\"123456\", " +
        "\"email\":\"klm\" }\n";

    when(service.addPerson(personTest1)).thenReturn(personTest1);

    mockMvc.perform(post("/persons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());


    //When
    //Then
  }

  @Test
  void addPersonNullObjectReturnStatut400() throws Exception {
    //Given
    Person personTest = new Person();
    personTest.setFirstName("A");
    personTest.setLastName("X");
    personTest.setAddress("adress");
    personTest.setCity("t");
    personTest.setZip(123);
    personTest.setPhone("123456");
    personTest.setEmail("klm");

    when(service.addPerson(personTest)).thenReturn(null);

    mockMvc.perform(post("/persons"))
        .andExpect(status().is4xxClientError());


  }
}
