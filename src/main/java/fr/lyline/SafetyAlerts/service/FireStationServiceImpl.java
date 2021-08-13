package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.FireStation;
import fr.lyline.SafetyAlerts.repository.FireStationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FireStationServiceImpl implements FireStationService {

  @Autowired
  FireStationRepo repository;

  public FireStationServiceImpl(FireStationRepo repository) {
    this.repository = repository;
  }

  @Override
  public List<Integer> getFireStation(String address) {
    List<Integer> list = new ArrayList<>();

    for (FireStation fs : repository.findAll()) {
      if (fs.getAddress().equals(address)) {
        list.add(fs.getStation());
      }
    }
    return list;
  }

  @Override
  public List<Integer> getAllFireStations() {
    List<Integer> list = new ArrayList<>();

    for (FireStation fs : repository.findAll()) {
      if (!list.contains(fs.getStation())) list.add(fs.getStation());
    }
    Collections.sort(list);
    return list;
  }

  @Override
  public void addFireStation(FireStation fireStation) {
    if (fireStation.getStation() != 0 && fireStation.getAddress() != null) {
      repository.add(fireStation);
    }
  }

  @Override
  public void addAllFireStations(List<FireStation> list) {
    for (FireStation fs : list) {
      if (fs.getStation() != 0 && fs.getAddress() != null) {
        repository.add(fs);
      }
    }
  }

  @Override
  public void upDateFireStation(String address, String fireStationId, FireStation fireStationToUpDate) {
    String id = fireStationId + "-" + address;
    FireStation fireStation = repository.findById(id);

    if (fireStation != null) {
      if (fireStationToUpDate.getStation() != 0
          && fireStationToUpDate.getAddress().equals(fireStation.getAddress())) {
        fireStation.setStation(fireStationToUpDate.getStation());
      }
    }
    repository.update(id, fireStation);
  }

  @Override
  public void removeFireStation(String fireStation, String address) {
    repository.deleteById(fireStation + "-" + address);
  }

  @Override
  public void removeAllFireStations() {
    repository.deleteAll();
  }
}
