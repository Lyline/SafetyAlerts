package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.FireStation;

import java.util.List;

public interface FireStationService {
  List<Integer> getFireStation(String address);

  List<Integer> getAllFireStations();

  boolean addFireStation(FireStation fs);

  boolean updateFireStation(Integer oldStationNumber, String address, FireStation fireStationToUpDate);

  boolean removeFireStation(String fireStation, String address);

  List<FireStation> getAllFireStationsObject();
}
