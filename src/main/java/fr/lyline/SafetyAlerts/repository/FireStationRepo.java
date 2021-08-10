package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.FireStation;

import java.util.List;

public interface FireStationRepo {
  List<FireStation> findAll();

  FireStation findById(String address);

  void add(FireStation fireStation);

  void addAll(List<FireStation> list);

  void update(String address, FireStation fireStationToUpDate);

  void deleteById(String id);

  void deleteAll();
}
