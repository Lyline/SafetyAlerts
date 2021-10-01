package fr.lyline.SafetyAlerts.controller;

import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.service.MedicalRecordServiceImpl;
import org.joda.time.DateTime;
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

@WebMvcTest(MedicalRecordController.class)
class MedicalRecordControllerTest {
  private final List<MedicalRecord> medicList = new ArrayList<>();
  @MockBean
  MedicalRecordServiceImpl service;
  MedicalRecord medic1 = new MedicalRecord("Homer", "Simpson", new DateTime("1956-05-12"),
      new String[]{"duff 250cl"}, new String[]{"work"});
  MedicalRecord medic2 = new MedicalRecord("Marge", "Simpson", new DateTime("1957-03-19"),
      new String[]{}, new String[]{});
  MedicalRecord medic3 = new MedicalRecord("Bart", "Simpson", new DateTime("2010-02-23"),
      new String[]{}, new String[]{"school", "vegetables"});
  @Autowired
  private MockMvc mvc;

  @BeforeEach
  void setUp() {
    medicList.add(medic1);
    medicList.add(medic2);
    medicList.add(medic3);
  }

  @Test
  void whenGetAllMedicalRecordsReturnStatus200() throws Exception {
    //Given
    List<MedicalRecord> medicList = new ArrayList<>();
    medicList.add(medic1);
    given(service.getAllMedicalRecords()).willReturn(medicList);

    //When
    MvcResult result = mvc.perform(get("/medicalrecords")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();
    //Then
    assertEquals("[{\"firstName\":\"Homer\"," +
        "\"lastName\":\"Simpson\"," +
        "\"birthdate\":\"05/12/1956\"," +
        "\"medications\":[\"duff 250cl\"]," +
        "\"allergies\":[\"work\"]}]", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetAllMedicalRecordsEmptyListReturnStatus404() throws Exception {
    //Given
    given(service.getAllMedicalRecords()).willReturn(List.of());

    //When
    MvcResult result = mvc.perform(get("/medicalrecords")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andReturn();
    //Then
    assertEquals("", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetExistingMedicalRecordReturnStatus200() throws Exception {
    //Given
    given(service.getMedicalRecordByFirstNameAndLastName("Homer", "Simpson")).willReturn(medic1);

    //When
    MvcResult result = mvc.perform(get("/medicalrecords/Homer_Simpson")
            .contentType(MediaType.APPLICATION_JSON)
            .param("firstName", "Homer")
            .param("lastName", "Simpson"))
        .andExpect(status().isOk())
        .andReturn();

    //Then
    assertEquals("{\"firstName\":\"Homer\"," +
        "\"lastName\":\"Simpson\"," +
        "\"birthdate\":\"05/12/1956\"," +
        "\"medications\":[\"duff 250cl\"]," +
        "\"allergies\":[\"work\"]}", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetNotExistingMedicalRecordReturnStatus404() throws Exception {
    //Given
    given(service.getMedicalRecordByFirstNameAndLastName("John", "Doe")).willReturn(null);

    //When
    MvcResult result = mvc.perform(get("/medicalrecords/John_Doe")
            .contentType(MediaType.APPLICATION_JSON)
            .param("firstName", "John")
            .param("lastName", "Doe"))
        .andExpect(status().isNotFound())
        .andReturn();

    //Then
    assertEquals("", result.getResponse().getContentAsString());
  }

  @Test
  void whenAddNewMedicalRecordReturnStatus201() throws Exception {
    //Given
    MedicalRecord medic = new MedicalRecord("John", "Doe", new DateTime("1956-05-12"),
        new String[]{}, new String[]{});

    given((service.getMedicalRecordByFirstNameAndLastName("John", "Doe"))).willReturn(null);

    //When
    MvcResult result = mvc.perform(post("/medicalrecords")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"firstName\":\"John\"," +
                "\"lastName\":\"Doe\"," +
                "\"birthdate\":\"12/05/1956\"," +
                "\"medications\":[]," +
                "\"allergies\":[]}"))
        .andExpect(status().isCreated())
        .andReturn();

    //Then
    assertEquals("", result.getResponse().getContentAsString());
  }

  @Test
  void whenAddExistMedicalRecordReturnStatus409() throws Exception {
    //Given
    MedicalRecord medic = new MedicalRecord("John", "Doe", new DateTime("1956-05-12"),
        new String[]{}, new String[]{});

    given((service.getMedicalRecordByFirstNameAndLastName("John", "Doe"))).willReturn(medic);

    //When
    MvcResult result = mvc.perform(post("/medicalrecords")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"firstName\":\"John\"," +
                "\"lastName\":\"Doe\"," +
                "\"birthdate\":\"12/05/1956\"," +
                "\"medications\":[]," +
                "\"allergies\":[]}"))
        .andExpect(status().isConflict())
        .andReturn();

    //Then
    assertEquals("", result.getResponse().getContentAsString());
  }

  @Test
  void whenUpdateExistingMedicalRecordReturnStatus200() throws Exception {
    //Given
    when(service.getMedicalRecordByFirstNameAndLastName("Homer", "Simpson")).thenReturn(medic1);

    //When
    MvcResult result = mvc.perform(patch("/medicalrecords/Homer_Simpson")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content("{\"firstName\":\"Homer\"," +
                "\"lastName\":\"Simpson\"," +
                "\"birthdate\":\"12/05/1956\"," +
                "\"medications\":[]," +
                "\"allergies\":[]}"))
        .andExpect(status().isOk())
        .andReturn();
    //Then
    assertEquals("", result.getResponse().getContentAsString());
  }

  @Test
  void whenUpdateExistingMedicalRecordReturnStatus304() throws Exception {
    //Given
    when(service.getMedicalRecordByFirstNameAndLastName("Homer", "Simpson")).thenReturn(null);

    //When
    MvcResult result = mvc.perform(patch("/medicalrecords/Homer_Simpson")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content("{\"firstName\":\"Homer\"," +
                "\"lastName\":\"Simpson\"," +
                "\"birthdate\":\"12/05/1956\"," +
                "\"medications\":[]," +
                "\"allergies\":[]}"))
        .andExpect(status().isNotModified())
        .andReturn();
    //Then
    assertEquals("", result.getResponse().getContentAsString());
  }

  @Test
  void whenDeleteExistingMedicalRecordReturnStatus200() throws Exception {
    //Given
    when(service.getMedicalRecordByFirstNameAndLastName("Homer", "Simpson")).thenReturn(medic1);
    when(service.removeMedicalRecordByFirstNameAndLastName("Homer", "Simpson")).thenReturn(true);

    //When
    MvcResult result = mvc.perform(delete("/medicalrecords/Homer_Simpson")
            .contentType(MediaType.APPLICATION_JSON)
            .param("firstName", "Homer")
            .param("lastName", "Simpson"))
        .andExpect(status().isOk())
        .andReturn();
    //Then
    assertEquals("", result.getResponse().getContentAsString());
  }

  @Test
  void whenDeleteNotExistingMedicalRecordReturnStatus404() throws Exception {
    //Given
    when(service.getMedicalRecordByFirstNameAndLastName("John", "Doe")).thenReturn(null);
    when(service.removeMedicalRecordByFirstNameAndLastName("Homer", "Simpson")).thenReturn(false);

    //When
    MvcResult result = mvc.perform(delete("/medicalrecords/John_Doe")
            .contentType(MediaType.APPLICATION_JSON)
            .param("firstName", "John")
            .param("lastName", "Doe"))
        .andExpect(status().isNotFound())
        .andReturn();
    //Then
    assertEquals("", result.getResponse().getContentAsString());
  }
}