package fr.lyline.SafetyAlerts.controller;

import fr.lyline.SafetyAlerts.repository.PersonRepoImpl;
import fr.lyline.SafetyAlerts.service.PersonService;
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
  private PersonService service;

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
  void addPersonReturnStatus200() throws Exception {
    //Given

    //When
    mockMvc.perform(post("/person"))
        .andExpect(status().isOk());
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
