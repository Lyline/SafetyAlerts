package fr.lyline.SafetyAlerts.controller;

import fr.lyline.SafetyAlerts.model.Person;
import fr.lyline.SafetyAlerts.service.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

  List<Person> personList = new ArrayList<>();

  @MockBean
  private PersonServiceImpl service;
  Person person1 = new Person("Homer", "Simpson", "742 Evergreen Terrace",
      "Springfield", 80085, "123-456", "homer@test.com");
  Person person2 = new Person("Marge", "Simpson", "742 Evergreen Terrace",
      "Springfield", 80085, "123-456", "homer@test.com");
  Person person3 = new Person("Bart", "Simpson", "742 Evergreen Terrace",
      "Springfield", 80085, "123-456", "homer@test.com");
  @Autowired
  private MockMvc mvc;

  @BeforeEach
  void setUp() {
    personList.add(person1);
    personList.add(person2);
    personList.add(person3);
  }

  @Test
  void whenGetAllPersonsReturnStatus200() throws Exception {
    //Given
    List<Person> personList = new ArrayList<>();
    personList.add(person1);
    given(service.getAllPersons()).willReturn(personList);

    //When
    MvcResult result = mvc.perform(get("/persons")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    //Then
    assertEquals("[{\"firstName\":\"Homer\"," +
        "\"lastName\":\"Simpson\"," +
        "\"address\":\"742 Evergreen Terrace\"," +
        "\"city\":\"Springfield\"," +
        "\"zip\":80085," +
        "\"phone\":\"123-456\"," +
        "\"email\":\"homer@test.com\"}]", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetAllPersonsEmptyListReturnStatut404() throws Exception {
    //Given
    given(service.getAllPersons()).willReturn(List.of());

    //When
    MvcResult result = mvc.perform(get("/persons")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andReturn();

    //Then
    assertEquals("[]", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetExistingPersonReturnStatus200() throws Exception {
    //Given
    given(service.getPerson("Homer", "Simpson")).willReturn(person1);

    //When
    MvcResult result = mvc.perform(get("/person/Homer_Simpson")
            .contentType(MediaType.APPLICATION_JSON)
            .param("firstName", "Homer")
            .param("lastName", "Simpson"))
        .andExpect(status().isOk())
        .andReturn();

    //Then
    assertEquals("{\"firstName\":\"Homer\"," +
        "\"lastName\":\"Simpson\"," +
        "\"address\":\"742 Evergreen Terrace\"," +
        "\"city\":\"Springfield\"," +
        "\"zip\":80085," +
        "\"phone\":\"123-456\"," +
        "\"email\":\"homer@test.com\"}", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetNotExistingPersonReturnStatus404() throws Exception {
    //Given
    given(service.getPerson("Homer", "Simpson")).willReturn(null);

    //When
    MvcResult result = mvc.perform(get("/persons/Homer_Simpson")
            .contentType(MediaType.APPLICATION_JSON)
            .param("firstName", "Homer")
            .param("lastName", "Simpson"))
        .andExpect(status().isNotFound())
        .andReturn();

    assertEquals("", result.getResponse().getContentAsString());
  }

  @Test
  void whenAddPersonReturnStatus404() throws Exception {
    //Given
    Person person = new Person("Jo", "Simpson", "NoWhere", "Springfield", 80085,
        "123-456", "jo@test.com");

    given(service.getPerson("Jo", "Simpson")).willReturn(null);
    given(service.addPerson(person)).willReturn(false);

    //When
    MvcResult result = mvc.perform(post("/person")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"firstName\":\"Jo\"," +
                "\"lastName\":\"Simpson\"," +
                "\"address\":\"NoWhere\"," +
                "\"city\":\"Springfield\"," +
                "\"zip\": 80085," +
                "\"phone\":\"123-456\"," +
                "\"email\":\"jo@test.com\"}"))
        .andExpect(status().isNotFound())
        .andReturn();
    //Then
    assertEquals("", result.getResponse().getContentAsString());
  }

  @Test
  void whenAddExistingPersonReturnStatus409() throws Exception {
    //Given
    given(service.getPerson("Homer", "Simpson")).willReturn(person1);

    //When
    MvcResult result = mvc.perform(post("/person")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"firstName\":\"Homer\"," +
                "\"lastName\":\"Simpson\"," +
                "\"address\":\"742 Evergreen Terrace\"," +
                "\"city\":\"Springfield\"," +
                "\"zip\": 80085," +
                "\"phone\":\"123-456\"," +
                "\"email\":\"homer@test.com\"}"))


        .andExpect(status().isConflict())
        .andReturn();
    //Then
    assertEquals("", result.getResponse().getContentAsString());
  }

  @Test
  void whenUpdateExistingPersonReturnStatus200() throws Exception {
    //Given
    given(service.getPerson("Homer", "Simpson")).willReturn(person1);
//When
    MvcResult result = mvc.perform(put("/person/Homer_Simpson")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content("{\"firstName\":\"Homer\"," +
                "\"lastName\":\"Simpson\"," +
                "\"address\":\"NoWhere\"," +
                "\"city\":\"Sin City\"," +
                "\"zip\": 80085," +
                "\"phone\":\"123-456\"," +
                "\"email\":\"homer@test.com\"}")
        )
        .andExpect(status().isOk())
        .andReturn();
    //Then
    assertEquals("{\"firstName\":\"Homer\"," +
        "\"lastName\":\"Simpson\"," +
        "\"address\":\"NoWhere\"," +
        "\"city\":\"Sin City\"," +
        "\"zip\":80085," +
        "\"phone\":\"123-456\"," +
        "\"email\":\"homer@test.com\"}", result.getResponse().getContentAsString());
  }

  @Test
  void whenUpdateExistingPersonReturnStatus404() throws Exception {
    //Given
    given(service.getPerson("Homer", "Simpson")).willReturn(null);
    //When
    MvcResult result = mvc.perform(put("/persons/Homer_Simpson")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content("{\"firstName\":\"Homer\"," +
                "\"lastName\":\"Simpson\"," +
                "\"address\":\"NoWhere\"," +
                "\"city\":\"Sin City\"," +
                "\"zip\": 80085," +
                "\"phone\":\"123-456\"," +
                "\"email\":\"homer@test.com\"}"))
        .andExpect(status().isNotFound())
        .andReturn();
    //Then
    assertEquals("", result.getResponse().getContentAsString());
  }

  @Test
  void whenRemoveExistingPersonReturnStatus200() throws Exception {
    //Given
    when(service.getPerson("Homer", "Simpson")).thenReturn(person1);
    when(service.removePerson("Homer", "Simpson")).thenReturn(true);

    //When
    MvcResult result = mvc.perform(delete("/person/Homer_Simpson")
            .contentType(MediaType.APPLICATION_JSON)
            .param("firstName", "Homer")
            .param("lastName", "Simpson"))
        .andExpect(status().isOk())
        .andReturn();

    //Then
    assertEquals("{\"firstName\":\"Homer\"," +
        "\"lastName\":\"Simpson\"," +
        "\"address\":\"742 Evergreen Terrace\"," +
        "\"city\":\"Springfield\"," +
        "\"zip\":80085," +
        "\"phone\":" +
        "\"123-456\"," +
        "\"email\":\"homer@test.com\"}", result.getResponse().getContentAsString());
  }

  @Test
  void whenRemoveExistingPersonReturnStatus404() throws Exception {
    //Given
    when(service.getPerson("Homer", "Simpson")).thenReturn(null);
    when(service.removePerson("Homer", "Simpson")).thenReturn(false);
    //When
    MvcResult result = mvc.perform(delete("/person/Homer_Simpson")
            .contentType(MediaType.APPLICATION_JSON)
            .param("firstName", "Homer")
            .param("lastName", "Simpson"))
        .andExpect(status().isNotFound())
        .andReturn();
    //Then
    assertEquals("", result.getResponse().getContentAsString());
  }
}