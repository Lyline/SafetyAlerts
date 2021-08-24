package fr.lyline.SafetyAlerts.controller;

import fr.lyline.SafetyAlerts.model.Person;
import fr.lyline.SafetyAlerts.repository.PersonRepoImpl;
import fr.lyline.SafetyAlerts.service.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PersonServiceImpl service;

  @MockBean
  private PersonRepoImpl repository;

  @BeforeEach
  void setUp() {
    service = new PersonServiceImpl(repository);
  }

  @Test
  void getPersonsReturnStatus200() throws Exception {
    //Given
    //When
    mockMvc.perform(get("/persons"))
        .andExpect(status().isOk());
    //Then
  }

  @Test
  void getPersonReturnStatus200() throws Exception {
    //Given

    //When
    mockMvc.perform(get("/person/Roger_Boyd"))
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
    personMock.setFirstName("John");
    personMock.setLastName("Doe");
    personMock.setAddress("New Address");
    personMock.setCity("New City");
    personMock.setZip(1234);
    personMock.setPhone("123-345");
    personMock.setEmail("john@aol.com");

    //When
    /*mockMvc.perform(post("/person")
        .param("firstName", "John")
        .param("lastName", "Doe")
        .param("address", "newAddress")
        .param("city", "newCity")
        .param("zip", "124")
        .param("phone", "123-123")
        .param("email", "john@aol.com")
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
}
