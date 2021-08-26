package fr.lyline.SafetyAlerts.controller;

import fr.lyline.SafetyAlerts.model.FireStation;
import fr.lyline.SafetyAlerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FireStationController {
  @Autowired
  FireStationService service;

  @GetMapping("/firestations")
  public ResponseEntity<List<Integer>> getFireStations() {
    List<Integer> response = service.getAllFireStations();

    if (!response.isEmpty()) {
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  @GetMapping("/firestations/{address}")
  public ResponseEntity<List<Integer>> getFireStation(@PathVariable(value = "address") String address) {
    List<Integer> response = service.getFireStation(address);

    if (!response.isEmpty()) {
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping("/firestations")
  public ResponseEntity<FireStation> addFireStation(@RequestBody FireStation fireStation) {
    List<Integer> response = service.getFireStation(fireStation.getAddress());

    if (!response.contains(fireStation.getStation())) {
      service.addFireStation(fireStation);
      return new ResponseEntity<>(fireStation, HttpStatus.OK);
    } else return new ResponseEntity<>(fireStation, HttpStatus.CONFLICT);
  }

  @PatchMapping("/firestations/{oldFS}-{address}")
  public ResponseEntity<FireStation> upDateFireStation(@RequestBody FireStation fireStation,
                                                       @PathVariable(value = "oldFS") Integer oldFS,
                                                       @PathVariable(value = "address") String address) {

    List<Integer> response = service.getFireStation(address);

    if (response.contains(oldFS)) {
      if (!response.contains(fireStation.getStation())) {
        service.upDateFireStation(address, oldFS.toString(), fireStation);
        return new ResponseEntity<>(fireStation, HttpStatus.OK);
      } else if (response.contains(fireStation.getStation())) {
        service.removeFireStation(oldFS.toString(), address);
        return new ResponseEntity<>(fireStation, HttpStatus.OK);
      }
    }
    return new ResponseEntity<>(fireStation, HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/firestations/{idStation}-{address}")
  public ResponseEntity deleteFireStation(@PathVariable(value = "idStation") Integer idStation,
                                          @PathVariable(value = "address") String address) {
    List<Integer> response = service.getFireStation(address);

    if (response.contains(idStation)) {
      service.removeFireStation(idStation.toString(), address);
      return new ResponseEntity(HttpStatus.OK);
    } else return new ResponseEntity(HttpStatus.NOT_FOUND);
  }
}
