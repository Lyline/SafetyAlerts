package fr.lyline.SafetyAlerts.controller;

import fr.lyline.SafetyAlerts.model.FireStation;
import fr.lyline.SafetyAlerts.service.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 The fire station controller of the CRUD application.

 @author Quesne GC
 @see fr.lyline.SafetyAlerts.service.FireStationService
 @since 0.1 */
@RestController
public class FireStationController {

  /**
   The fire station service.

   @see fr.lyline.SafetyAlerts.service.FireStationService
   */
  @Autowired
  FireStationService service;

  /**
   The Logger.

   @see org.apache.logging.log4j.LogManager;
   @see org.apache.logging.log4j.Logger;
   */
  Logger logger = LogManager.getLogger(FireStationController.class);

  /**
   Gets all fire stations when the service return a list of fire station numbers. It return a list of fire station numbers
   and a HttpStatus 200, else return an empty list and a HttpStatus 404.

   @return the list of fire station numbers
   */
  @GetMapping("/firestations")
  public ResponseEntity<List<Integer>> getAllFireStations() {
    List<Integer> response = service.getAllFireStations();

    if (!response.isEmpty()) {
      logger.info("GET /firestations : " + response.size() + " station(s) - Status 200");
      logger.info(response);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      logger.warn("GET /firestations : No Data found - Status 404");
      logger.info(response);
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
  }

  /**
   Gets the fire station deserved when the address entered in parameter. If the service return a result then it
   returns a fire station number and a HttpStatus 200, else an empty list and a HttpStatus 404.

   @param address the address

   @return the deserved fire station number
   */
  @GetMapping("/firestations/{address}")
  public ResponseEntity<List<Integer>> getFireStation(@PathVariable(value = "address") String address) {
    List<Integer> response = service.getFireStation(address);

    if (!response.isEmpty()) {
      logger.info("GET /firestations/" + address + " - Status 200");
      logger.info(response);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      logger.warn("GET /firestations/" + address + " - Status 404");
      logger.info("null");
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  /**
   Add a new fire station when a fire station object entered in parameter. If the service return true then it return the
   added fire stattion and a HttpStatus 201, else if the service return an uncompleted object then it return a HttpStatus
   404, or if the fire station already exist then it return null and a HttpStatus 409.

   @param fireStation the fire station

   @return the response entity
   */
  @PostMapping("/firestations")
  public ResponseEntity addFireStation(@RequestBody FireStation fireStation) {
    List<Integer> response = service.getFireStation(fireStation.getAddress());
    if (fireStation.getStation() == null | fireStation.getAddress() == null) {
      logger.warn("POST /firestations " + fireStation.getStation() + "-" + fireStation.getAddress() +
          " - Incomplete informations for creation - Status 404");
      logger.info(response);
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    if (!response.contains(fireStation.getStation())) {
      service.addFireStation(fireStation);
      logger.info("POST /firestations : " + fireStation.getStation() + "-" + fireStation.getAddress() + " - Status 201");
      logger.info(fireStation.toString());
      return new ResponseEntity<>(fireStation, HttpStatus.CREATED);
    } else {
      logger.warn("POST /firestations : " + fireStation.getStation() + "-" + fireStation.getAddress() +
          " - Station already exist - Status 409");
      logger.info("null");
      return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }
  }

  /**
   Update a fire station when the station number, the intervention address of the fire station to update entered in parameters
   and the fire station object entered in request body. If the service return true then it returns the updated fire station
   and a HttpStatus 200, else it returns null and a HttpStatus 404.

   @param fireStation the new update fire station
   @param oldFS       the fire station number to update
   @param address     the intervention address to update

   @return the response entity
   */
  @PatchMapping("/firestations/{oldFS}-{address}")
  public ResponseEntity<FireStation> upDateFireStation(@RequestBody FireStation fireStation,
                                                       @PathVariable(value = "oldFS") Integer oldFS,
                                                       @PathVariable(value = "address") String address) {
    boolean result = service.updateFireStation(oldFS, address, fireStation);

    if (result) {
      logger.info("PATCH /firestations/" + fireStation.getStation() + "-" + fireStation.getAddress() + " - Status 200");
      logger.info("Exist : " + oldFS + "-" + address + "\nUpdate : " + fireStation);
      return new ResponseEntity<>(fireStation, HttpStatus.OK);
    } else {
      logger.warn("PATCH /firestations/" + oldFS + "-" + address + " - Status 404");
      logger.info("null");
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  /**
   Delete a fire station when the fire station number and the intervention address entered in parameters. If the service
   return true then it returns a HttpStatus 200, else return a HttpStatus 404.

   @param stationNumber the station number
   @param address       the address

   @return the response entity
   */
  @DeleteMapping("/firestations/{stationNumber}-{address}")
  public ResponseEntity<List<Integer>> deleteFireStation(@PathVariable() String stationNumber,
                                                         @PathVariable() String address) {
    List<Integer> response = service.getFireStation(address);

    if (response.contains(Integer.valueOf(stationNumber))) {
      service.removeFireStation(stationNumber, address);
      logger.info("DELETE /firestations/" + stationNumber + "-" + address + " - Status 200");
      logger.info("Exist : " + stationNumber + "-" + address);
      return new ResponseEntity(HttpStatus.OK);
    } else {
      logger.warn("DELETE /firestations/" + stationNumber + "-" + address + " - Fire station not exist - Status 404");
      logger.info("null");
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
  }
}
