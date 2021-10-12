package fr.lyline.SafetyAlerts.controller;

import com.google.inject.internal.util.ImmutableSet;
import fr.lyline.SafetyAlerts.ObjectMapper.ChildAlert;
import fr.lyline.SafetyAlerts.ObjectMapper.PersonInfo;
import fr.lyline.SafetyAlerts.model.Person;
import fr.lyline.SafetyAlerts.service.MainFunctionsAPIService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MainFunctionsAPIController.class)
class MainFunctionsAPIControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private MainFunctionsAPIService service;

  @Test
  void whenGetCommunityEmailWithTwoEmailsThenStatus200() throws Exception {
    //Given
    Set<String> emailSet = ImmutableSet.of("homer@test.com", "test@test.com");
    given(service.getCommunityEmail("Springfield")).willReturn(emailSet);

    //When
    MvcResult result = mvc.perform(get("/communityEmail")
            .contentType(MediaType.APPLICATION_JSON)
            .param("city", "Springfield"))
        .andExpect(status().isOk())
        .andReturn();

    //Then
    assertEquals("[\"homer@test.com\",\"test@test.com\"]", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetCommunityEmailWithoutEmailThenStatus404() throws Exception {
    //Given
    Set<String> emailSet = Set.of();
    given(service.getCommunityEmail("Springfield")).willReturn(emailSet);

    //When
    MvcResult result = mvc.perform(get("/communityEmail")
            .contentType(MediaType.APPLICATION_JSON)
            .param("city", "Springfield"))
        .andExpect(status().isNotFound())
        .andReturn();

    //Then
    assertEquals("[]", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetPhoneAlertWithTwoPhoneNumberThenStatus200() throws Exception {
    //Given
    Set<String> phoneSet = ImmutableSet.of("123-456", "456-789");
    given(service.getPhoneForAlert(1)).willReturn(phoneSet);

    //When
    MvcResult result = mvc.perform(get("/phoneAlert")
            .contentType(MediaType.APPLICATION_JSON)
            .param("firestation", String.valueOf(1)))
        .andExpect(status().isOk())
        .andReturn();

    //Then
    assertEquals("[\"123-456\",\"456-789\"]", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetPhoneAlertWithoutPhoneNumberThenStatus404() throws Exception {
    //Given
    Set<String> phoneSet = Set.of();
    given(service.getPhoneForAlert(1)).willReturn(phoneSet);

    //When
    MvcResult result = mvc.perform(get("/phoneAlert")
            .contentType(MediaType.APPLICATION_JSON)
            .param("firestation", String.valueOf(1)))
        .andExpect(status().isNotFound())
        .andReturn();

    //Then
    assertEquals("[]", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetPersonInfoExistingThenStatus200() throws Exception {
    //Given
    PersonInfo personInfo = new PersonInfo("Homer", "Simpson", "123-456", "homer@test.com",
        65, new String[]{"Duff : 250 ml"}, new String[]{"work"});

    when(service.getPersonInfo("Homer", "Simpson")).thenReturn(personInfo);

    //When
    MvcResult result = mvc.perform(get("/personInfo")
            .contentType(MediaType.APPLICATION_JSON)
            .param("firstName", "Homer")
            .param("lastName", "Simpson"))
        .andExpect(status().isOk())
        .andReturn();

    //Then
    assertEquals("{\"firstName\":\"Homer\"," +
        "\"lastName\":\"Simpson\"," +
        "\"phone\":\"123-456\"," +
        "\"email\":\"homer@test.com\"," +
        "\"age\":65," +
        "\"medications\":[\"Duff : 250 ml\"]," +
        "\"allergies\":[\"work\"]}", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetPersonInfoExistingThenStatus404() throws Exception {
    //Given
    when(service.getPersonInfo("Homer", "Simpson")).thenReturn(null);

    //When
    MvcResult result = mvc.perform(get("/personInfo")
            .contentType(MediaType.APPLICATION_JSON)
            .param("firstName", "Homer")
            .param("lastName", "Simpson"))
        .andExpect(status().isNotFound())
        .andReturn();

    //Then
    assertEquals("", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetChildAlertWithOneChildAndTwoParentsThenStatus200() throws Exception {
    //Given
    Person father = new Person("Homer", "Simpson", "742 Evergreen Terrace",
        "Springfield", 80085, "123-456", "homer@test.com");
    Person mother = new Person("Marge", "Simpson", "742 Evergreen Terrace",
        "Springfield", 80085, "123-456", "marge@test.com");
    Person child = new Person("Bart", "Simpson", "742 Evergreen Terrace",
        "Springfield", 80085, "123-456", "bart@test.com");

    List<Person> parents = new ArrayList<>();
    parents.add(father);
    parents.add(mother);

    ChildAlert childAlert = new ChildAlert(child, "11", parents);
    List<ChildAlert> childAlertList = new ArrayList<>();
    childAlertList.add(childAlert);

    when(service.getChildAlert("742 Evergreen Terrace")).thenReturn(childAlertList);

    //When
    MvcResult result = mvc.perform(get("/childAlert")
            .contentType(MediaType.APPLICATION_JSON)
            .param("address", "742 Evergreen Terrace"))
        .andExpect(status().isOk())
        .andReturn();

    //Then
    assertEquals("[{\"parents\":" +
            "[{\"parentFirstName\":\"Homer\",\"parentLastName\":\"Simpson\"}," +
            "{\"parentFirstName\":\"Marge\",\"parentLastName\":\"Simpson\"}]," +
            "\"childFirstName\":\"Bart\",\"childLastName\":\"Simpson\",\"age\":\"11\"}]",
        result.getResponse().getContentAsString());
  }

  @Test
  void whenGetChildAlertWithOneChildAndTwoParentsThenStatus204() throws Exception {
    //Given
    List<ChildAlert> childAlertList = new ArrayList<>();

    when(service.getChildAlert("742 Evergreen Terrace")).thenReturn(childAlertList);

    //When
    MvcResult result = mvc.perform(get("/childAlert")
            .contentType(MediaType.APPLICATION_JSON)
            .param("address", "742 Evergreen Terrace"))
        .andExpect(status().isNoContent())
        .andReturn();

    //Then
    assertEquals("", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetFloodStationWithOneStationOneAddressAndTwoPersonsThenStatus200() throws Exception {
    //Given
    List<PersonInfo> personInfoList = new ArrayList<>();
    Map<String, List<PersonInfo>> residentList = new HashMap<>();
    Map<Integer, List<Map<String, List<PersonInfo>>>> residentsByAddressMap = new HashMap<>();

    PersonInfo person1 = new PersonInfo("Homer", "Simpson", "123-456",
        65, new String[]{"Duff : 250ml"}, new String[]{"work"});
    PersonInfo person2 = new PersonInfo("Marge", "Simpson", "123-456",
        60, new String[]{}, new String[]{});
    personInfoList.add(person1);
    personInfoList.add(person2);

    residentList.put("742 Evergreen Terrace", personInfoList);
    residentsByAddressMap.put(1, List.of(residentList));

    when(service.getResidentsContactFromToFireStation(new int[]{1})).thenReturn(residentsByAddressMap);

    //When
    MvcResult result = mvc.perform(get("/flood/stations")
            .contentType(MediaType.APPLICATION_JSON)
            .param("stations", "1"))
        .andExpect(status().isOk())
        .andReturn();
    //Then
    assertEquals("{\"1\":" +
        "[{\"742 Evergreen Terrace\":" +
        "[{\"firstName\":\"Homer\"," +
        "\"lastName\":\"Simpson\"," +
        "\"phone\":\"123-456\"," +
        "\"email\":null," +
        "\"age\":65," +
        "\"medications\":[\"Duff : 250ml\"]," +
        "\"allergies\":[\"work\"]}," +
        "{\"firstName\":\"Marge\"," +
        "\"lastName\":\"Simpson\"," +
        "\"phone\":\"123-456\"," +
        "\"email\":null," +
        "\"age\":60," +
        "\"medications\":[]," +
        "\"allergies\":[]}]}]}", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetFloodStationWithOneStationWithoutAddressAndFamilyThenStatus204() throws Exception {
    //Given
    Map<Integer, List<Map<String, List<PersonInfo>>>> residentsByAddressMap = new HashMap<>();

    when(service.getResidentsContactFromToFireStation(new int[]{1})).thenReturn(residentsByAddressMap);

    //When
    MvcResult result = mvc.perform(get("/flood/stations")
            .contentType(MediaType.APPLICATION_JSON)
            .param("stations", "1"))
        .andExpect(status().isNoContent())
        .andReturn();
    //Then
    assertEquals("{}", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetPersonsInfoByStationWithTwoAdultsAndOneChildThenStatus200() throws Exception {
    //Given
    Map<String, List<Map<String, String>>> informationList = new HashMap<>();

    Map<String, String> fatherMap = new HashMap<>();
    fatherMap.put("firstName", "Homer");
    fatherMap.put("lastName", "Simpson");
    fatherMap.put("address", "742 Evergreen Terrace");
    fatherMap.put("phone", "123-456");

    Map<String, String> motherMap = new HashMap<>();
    motherMap.put("firstName", "Marge");
    motherMap.put("lastName", "Simpson");
    motherMap.put("address", "742 Evergreen Terrace");
    motherMap.put("phone", "123-456");

    Map<String, String> childMap = new HashMap<>();
    childMap.put("firstName", "Bart");
    childMap.put("lastName", "Simpson");
    childMap.put("address", "742 Evergreen Terrace");
    childMap.put("phone", "123-456");

    List<Map<String, String>> personList = new ArrayList<>();
    personList.add(fatherMap);
    personList.add(motherMap);
    personList.add(childMap);

    Map<String, String> countMap = new HashMap<>();
    countMap.put("children", "1");
    countMap.put("adults", "2");

    List<Map<String, String>> count = new ArrayList<>();
    count.add(countMap);

    informationList.put("persons", personList);
    informationList.put("count", count);

    when(service.getPersonsInfoByStation(1)).thenReturn(informationList);

    //When
    MvcResult result = mvc.perform(get("/firestation")
            .contentType(MediaType.APPLICATION_JSON)
            .param("stationNumber", "1"))
        .andExpect(status().isOk())
        .andReturn();

    //Then
    assertEquals("{\"" +
        "persons\":" +
        "[" +
        "{\"firstName\":\"Homer\"," +
        "\"lastName\":\"Simpson\"," +
        "\"address\":\"742 Evergreen Terrace\"," +
        "\"phone\":\"123-456\"}," +
        "{\"firstName\":\"Marge\"," +
        "\"lastName\":\"Simpson\"," +
        "\"address\":\"742 Evergreen Terrace\"," +
        "\"phone\":\"123-456\"}," +
        "{\"firstName\":\"Bart\"," +
        "\"lastName\":\"Simpson\"," +
        "\"address\":\"742 Evergreen Terrace\"," +
        "\"phone\":\"123-456\"}" +
        "]," +
        "\"count\":" +
        "[" +
        "{\"children\":\"1\"," +
        "\"adults\":\"2\"}" +
        "]}", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetPersonsInfoByStationWithTwoAdultsAndOneChildThenStatus204() throws Exception {
    //Given
    Map<String, List<Map<String, String>>> informationList = new HashMap<>();

    when(service.getPersonsInfoByStation(1)).thenReturn(informationList);

    //When
    MvcResult result = mvc.perform(get("/firestation")
            .contentType(MediaType.APPLICATION_JSON)
            .param("stationNumber", "1"))
        .andExpect(status().isNoContent())
        .andReturn();

    //Then
    assertEquals("{}", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetPersonsInfoByAddressWithOnePersonAndTwoFireStationThenStatus200() throws Exception {
    //Given
    List<Integer> fireStations = new ArrayList<>();
    fireStations.add(1);
    fireStations.add(2);

    Map<String, String> personInfo = new HashMap<>();
    personInfo.put("phone", "123-456");
    personInfo.put("age", "65");
    personInfo.put("medications", "Duff : 250ml");
    personInfo.put("allergies", "work");

    Map<String, Object> resident = new HashMap<>();
    resident.put("firstName", "Homer");
    resident.put("lastName", "Simpson");
    resident.put("information", personInfo);

    List<Object> personInfoList = new ArrayList<>();
    personInfoList.add(resident);

    Map<String, List<Object>> informationMap = new HashMap<>();
    informationMap.put("firestation", Collections.singletonList(fireStations));
    informationMap.put("persons", personInfoList);

    when(service.getPersonsInfoByAddress("742 Evergreen Terrace")).thenReturn(informationMap);

    //When
    MvcResult result = mvc.perform(get("/fire")
            .contentType(MediaType.APPLICATION_JSON)
            .param("address", "742 Evergreen Terrace"))
        .andExpect(status().isOk())
        .andReturn();
    //Then
    assertEquals("{" +
        "\"persons\":[" +
        "{\"firstName\":\"Homer\"," +
        "\"lastName\":\"Simpson\"," +
        "\"information\":" +
        "{\"allergies\":\"work\"," +
        "\"phone\":\"123-456\"," +
        "\"medications\":\"Duff : 250ml\"," +
        "\"age\":\"65\"}" +
        "}]," +
        "\"firestation\":[[1,2]]}", result.getResponse().getContentAsString());
  }

  @Test
  void whenGetPersonsInfoByAddressWithoutPersonThenStatus204() throws Exception {
    //Given
    Map<String, List<Object>> informationMap = new HashMap<>();

    when(service.getPersonsInfoByAddress("742 Evergreen Terrace")).thenReturn(informationMap);

    //When
    MvcResult result = mvc.perform(get("/fire")
            .contentType(MediaType.APPLICATION_JSON)
            .param("address", "742 Evergreen Terrace"))
        .andExpect(status().isNoContent())
        .andReturn();
    //Then
    assertEquals("", result.getResponse().getContentAsString());
  }
}