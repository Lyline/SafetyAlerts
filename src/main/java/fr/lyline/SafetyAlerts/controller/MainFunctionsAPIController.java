package fr.lyline.SafetyAlerts.controller;

import fr.lyline.SafetyAlerts.ObjectMapper.ChildAlert;
import fr.lyline.SafetyAlerts.ObjectMapper.PersonInfo;
import fr.lyline.SafetyAlerts.service.MainFunctionsAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class MainFunctionsAPIController {
  @Autowired
  MainFunctionsAPIService service;

  @GetMapping("/communityEmail")
  public ResponseEntity<Set<String>> getCommunityEmail(@RequestParam() String city) {
    Set<String> response = service.getCommunityEmail(city);

    if (!response.isEmpty()) {
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/phoneAlert")
  public ResponseEntity<Set<String>> getPhoneAlert(@RequestParam(value = "firestation") Integer fireStation_number) {
    Set<String> response = service.getPhoneForAlert(fireStation_number);

    if (!response.isEmpty()) {
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/personInfo")
  public ResponseEntity<PersonInfo> getPersonInfo(@RequestParam() String firstName,
                                                  @RequestParam() String lastName) {
    PersonInfo response = service.getPersonInfo(firstName, lastName);

    if (response != null) {
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/childAlert")
  public ResponseEntity<List<ChildAlert>> getChildAlert(
      @RequestParam() String address) {
    List<ChildAlert> response = service.getChildAlert(address);

    if (!response.isEmpty()) {
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/flood/stations")
  public ResponseEntity<Map<Integer, List<Map<String, List<PersonInfo>>>>> getFloodStation(
      @RequestParam(value = "stations") int[] stationList) {
    Map<Integer, List<Map<String, List<PersonInfo>>>> response = service.getResidentsContactFromToFireStation(stationList);

    if (!response.isEmpty()) {
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/firestation")
  public ResponseEntity<Map<String, List<Map<String, String>>>> getPersonsInfoByStation(
      @RequestParam() int stationNumber) {
    Map<String, List<Map<String, String>>> response = service.getPersonsInfoByStation(stationNumber);

    if (!response.isEmpty()) {
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/fire")
  public ResponseEntity<Map<String, List<Object>>> getPersonsInfoByAddress(
      @RequestParam() String address) {
    Map<String, List<Object>> response = service.getPersonsInfoByAddress(address);

    if (!response.isEmpty()) {
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
