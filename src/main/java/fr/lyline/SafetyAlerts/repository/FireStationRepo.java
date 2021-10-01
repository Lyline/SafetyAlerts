package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.FireStation;

import java.util.List;

public interface FireStationRepo {
  List<FireStation> findAll();

  FireStation findByStationNumberAndAddress(Integer stationNumber, String address);

  boolean add(FireStation fireStation);

  boolean update(Integer oldStationNumber, String address, FireStation fireStationToUpdate);

  boolean deleteByStationAndAddress(Integer stationNumber, String address);
}
