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

/**
 The controller of Main functions api controller.

 @author Quesne GC
 @see fr.lyline.SafetyAlerts.service.MainFunctionsAPIService
 @since 0.1 */
@RestController
public class MainFunctionsAPIController {
  /**
   The Service.

   @see fr.lyline.SafetyAlerts.service.MainFunctionsAPIService
   */
  @Autowired
  MainFunctionsAPIService service;

  /**
   The Logger.

   @see org.apache.logging.log4j.LogManager;
   @see org.apache.logging.log4j.Logger;
   */
  Logger logger = LogManager.getLogger(MainFunctionsAPIController.class);

  /**
   Gets the list of community email when its entered the city in parameter. Its return a list of email and a HttpStatus 200
   when the service return a set list, else return an empty list and a HttpStatus 404.

   @param city the city

   @return the list of community email

   @see fr.lyline.SafetyAlerts.service.MainFunctionsAPIService
   */
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

  /**
   Gets a list of phone numbers served by fire station when its entered the fire station number in parameter. Its return a
   list of phone number and a HttpStatus 200 when the service return a list of phone numbers, else return an empty list
   and a HttpStatus 404.

   @param fireStation_number the fire station number

   @return the list of phone numbers
   */
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

  /**
   Gets the person information when its entered the first name and the last name in parameter. Its return this person
   information of this person and a HttpStatus 200 when the service return this person information, else its return null
   and a HttpStatus 404.

   @param firstName the first name
   @param lastName  the last name

   @return the person info

   @see fr.lyline.SafetyAlerts.service.MainFunctionsAPIService
   */
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

  /**
   Gets the list of children with their parents when its entered an address in parameter. Its return a list of ChildAlert
   and a HttpStatus 200 when the service return this list, else its return null and a HttpStatus 204.

   @param address the address

   @return the list of ChildAlert

   @see fr.lyline.SafetyAlerts.service.MainFunctionsAPIService
   */
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

  /**
   Gets a list of persons information sorted by fire stations when an array of fire station number entered in parameter.
   Its return a list of person information (identification and medical) sorted by fire station and a HttpStatus 200 when the
   service return this result, else its return an empty list and a HttpStatus 204.

   @param stationList the array of fire station number

   @return the list of person sorted by fire station

   @see fr.lyline.SafetyAlerts.service.MainFunctionsAPIService
   */
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

  /**
   Gets the list of person information with the sum of adults and children when the fire station number entered in
   parameter. Its return a list of person information and the sum of adults and children and a HttpStatus 200 when the
   service return its result, else its return an empty list and a HttpStatus 204.

   @param stationNumber the station number

   @return the list of person information and the sum of adults and children

   @see fr.lyline.SafetyAlerts.service.MainFunctionsAPIService
   */
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

  /**
   Gets a list of persons with all information when an address entered in parameter. Its return a map of person
   information and a HttpStatus 200 when the service return its result, els its return an empty map and a HttpStatus 204

   @param address the address

   @return the map of person information leave to this address
   */
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
