package fr.lyline.SafetyAlerts.controller;

import fr.lyline.SafetyAlerts.model.FireStation;
import fr.lyline.SafetyAlerts.service.FireStationServiceImpl;
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

@WebMvcTest(FireStationController.class)
public class FireStationControllerTest {

  private final List<FireStation> stationList = new ArrayList<>();
  FireStation station1 = new FireStation(1, "742 Evergreen Terrace");
  FireStation station2 = new FireStation(1, "42 Wallaby Way");
  FireStation station3 = new FireStation(2, "42 Wallaby Way");
  @Autowired
  private MockMvc mvc;
  @MockBean
  private FireStationServiceImpl service;

  @BeforeEach
  void setUp() {
    stationList.add(station1);
    stationList.add(station2);
    stationList.add(station3);
  }

  @Test
  void whenGetAllFireStationsReturnStatus200() throws Exception {
    //Given
    when(service.getAllFireStations()).thenReturn(List.of(1, 2, 3));

    //When
    MvcResult result = mvc.perform(get("/firestations")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    //Then
    assertEquals("[1,2,3]", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetAllFireStationsNoContentReturnStatus404() throws Exception {
    //Given
    when(service.getAllFireStations()).thenReturn(List.of());

    //When
    MvcResult result = mvc.perform(get("/firestations")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andReturn();

    //Then
    assertEquals("[]", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetFireStationWithAnValidAddressReturnStatus200() throws Exception {
    //Given
    when(service.getFireStation("42 Wallaby Way")).thenReturn(List.of(1, 2));

    //When
    MvcResult result = mvc.perform(get("/firestation/42 Wallaby Way")
            .contentType(MediaType.APPLICATION_JSON)
            .param("address", "42 Wallaby Way"))
        .andExpect(status().isOk())
        .andReturn();

    //Then
    assertEquals("[1,2]", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetFireStationWithNotValidAddressReturnStatus404() throws Exception {
    //Given
    when(service.getFireStation("NoWhere")).thenReturn(List.of());

    //When
    MvcResult result = mvc.perform(get("/firestation/NoWhere")
            .contentType(MediaType.APPLICATION_JSON)
            .param("address", "NoWhere"))
        .andExpect(status().isNotFound())
        .andReturn();

    //Then
    assertEquals("", result.getResponse().getContentAsString());
  }

  @Test
  void whenAddValidFireStationReturnStatus201() throws Exception {
    //Given
    FireStation station = new FireStation(5, "Nowhere");
    when(service.getFireStation("Nowhere")).thenReturn(List.of(5));
    given(service.addFireStation(station)).willReturn(true);

    //When
    MvcResult result = mvc.perform(post("/firestation")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"station\":5 ," +
                "\"address\":\"NoWhere\"}"))
        .andExpect(status().isCreated())
        .andReturn();

    //Then
    assertEquals("{\"station\":5,\"address\":\"NoWhere\"}", result.getResponse().getContentAsString());
  }

  @Test
  void whenUpdateExistingPersonReturnStatus404() throws Exception {
    //Given
    FireStation station = new FireStation(5, "Nowhere");
    when(service.updateFireStation(2, "Nowhere", station)).thenReturn(false);

    //When
    MvcResult result = mvc.perform(put("/firestation/2-Nowhere")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content("{\"station\":5," +
                "\"address\":\"NewCity\"}"))
        .andExpect(status().isNotFound())
        .andReturn();

    //Then
    assertEquals("", result.getResponse().getContentAsString());
  }

  @Test
  void whenRemoveExistingAddressFromStationReturnStatus200() throws Exception {
    //Given
    when(service.getFireStation("42 Wallaby Way")).thenReturn(List.of(1, 2));
    when(service.removeFireStation("1", "42 Wallaby Way")).thenReturn(true);

    //When
    MvcResult result = mvc.perform(delete("/firestation/1-42 Wallaby Way")
            .contentType(MediaType.APPLICATION_JSON)
            .param("stationNumber", "1")
            .param("address", "42 Wallaby Way"))
        .andExpect(status().isOk())
        .andReturn();

    //Then
    assertEquals("", result.getResponse().getContentAsString());
  }

  @Test
  void whenRemoveNotExistingAddressFromStationReturnStatus204() throws Exception {
    //Given
    when(service.getFireStation("NoWhere")).thenReturn(List.of());
    when(service.removeFireStation("1", "NoWhere")).thenReturn(false);

    //When
    MvcResult result = mvc.perform(delete("/firestation/1-NoWhere")
            .contentType(MediaType.APPLICATION_JSON)
            .param("stationNumber", "1")
            .param("address", "NoWhere"))
        .andExpect(status().isNotFound())
        .andReturn();

    //Then
    assertEquals("", result.getResponse().getContentAsString());
  }
}
