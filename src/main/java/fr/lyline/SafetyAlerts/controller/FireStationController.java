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

@RestController
public class FireStationController {

  @Autowired
  FireStationService service;

  Logger logger = LogManager.getLogger(FireStationController.class);

  @GetMapping("/firestations")
  public ResponseEntity<List<Integer>> getFireStations() {
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
