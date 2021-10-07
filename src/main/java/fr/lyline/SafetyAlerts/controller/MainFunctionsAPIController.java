package fr.lyline.SafetyAlerts.controller;

import fr.lyline.SafetyAlerts.ObjectMapper.ChildAlert;
import fr.lyline.SafetyAlerts.ObjectMapper.PersonInfo;
import fr.lyline.SafetyAlerts.service.MainFunctionsAPIService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class MainFunctionsAPIController {
  @Autowired
  MainFunctionsAPIService service;

  Logger logger = LogManager.getLogger(MainFunctionsAPIController.class);

  @GetMapping("/communityEmail")
  public ResponseEntity<Set<String>> getCommunityEmail(@RequestParam() String city) {
    Set<String> response = service.getCommunityEmail(city);

    if (!response.isEmpty()) {
      logger.info("GET /communityEmail : " + response.size() + " email(s) at " + city + " - Status 200");
      logger.info(response);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      logger.warn("GET /communityEmail : Data not exist at " + city + " - Status 404");
      logger.info(response);
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/phoneAlert")
  public ResponseEntity<Set<String>> getPhoneAlert(@RequestParam(value = "firestation") Integer fireStation_number) {
    Set<String> response = service.getPhoneForAlert(fireStation_number);

    if (!response.isEmpty()) {
      logger.info("GET /phoneAlert?firestation=" + fireStation_number + " : " + response.size() + " phone number(s) - Status 200");
      logger.info(response);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      logger.warn("GET /phoneAlert?firestation=" + fireStation_number + " : Data not exist - Status 404");
      logger.info(response);
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/personInfo")
  public ResponseEntity<PersonInfo> getPersonInfo(@RequestParam() String firstName,
                                                  @RequestParam() String lastName) {
    PersonInfo response = service.getPersonInfo(firstName, lastName);

    if (response != null) {
      logger.info("GET /personInfo?firstName=" + firstName + "&lastName=" + lastName + " - Statut 200");
      logger.info(response);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      logger.warn("GET /personInfo?firstName=" + firstName + "&lastName=" + lastName + " : Data not exist - Statut 404");
      logger.info("null");
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/childAlert")
  public ResponseEntity<List<ChildAlert>> getChildAlert(@RequestParam() String address) {
    List<ChildAlert> response = service.getChildAlert(address);

    if (!response.isEmpty()) {
      logger.info("GET /childAlert?address=" + address + " - Status 200");
      logger.info(response);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      logger.warn("GET /childAlert?address=" + address + " - Data not exist - Status 204");
      logger.info(response);
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @GetMapping("/flood/stations")
  public ResponseEntity<Map<Integer, List<Map<String, List<PersonInfo>>>>> getFloodStation(
      @RequestParam(value = "stations") int[] stationList) {
    Map<Integer, List<Map<String, List<PersonInfo>>>> response = service.getResidentsContactFromToFireStation(stationList);

    if (!response.isEmpty()) {
      logger.info("GET /flood/stations?stations=" + Arrays.toString(stationList) + " - Status 200");
      logger.info(response);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      logger.warn("GET /flood/stations?stations=" + Arrays.toString(stationList) + " - Data not exist - Status 204");
      logger.info(response);
      return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
  }

  @GetMapping("/firestation")
  public ResponseEntity<Map<String, List<Map<String, String>>>> getPersonsInfoByStation(
      @RequestParam() int stationNumber) {
    Map<String, List<Map<String, String>>> response = service.getPersonsInfoByStation(stationNumber);

    if (!response.isEmpty()) {
      logger.info("GET /firestation?stationNumber=" + stationNumber + " - Status 200");
      logger.info(response);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      logger.warn("GET /firestation?stationNumber=" + stationNumber + " - Data not exist - 204");
      logger.info(response);
      return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
  }

  @GetMapping("/fire")
  public ResponseEntity<Map<String, List<Object>>> getPersonsInfoByAddress(
      @RequestParam() String address) {
    Map<String, List<Object>> response = service.getPersonsInfoByAddress(address);

    if (!response.isEmpty()) {
      logger.info("GET /fire?address=" + address + " - Status 200");
      logger.info(response);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      logger.warn("GET /fire?address=" + address + " - Data not exist - Status 204");
      logger.info(response);
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }
}
