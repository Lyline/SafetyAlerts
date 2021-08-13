package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.FireStation;

import java.util.List;

public interface FireStationService {
  List<Integer> getFireStation(String address);

  List<Integer> getAllFireStations();

  void addFireStation(FireStation fs);

  void addAllFireStations(List<FireStation> list);

  void upDateFireStation(String oldAddress, String oldFireStationId, FireStation fireStationToUpDate);

  void removeFireStation(String fireStation, String address);

  void removeAllFireStations();
}
