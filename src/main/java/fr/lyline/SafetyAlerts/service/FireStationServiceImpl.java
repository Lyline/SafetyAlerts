package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.FireStation;
import fr.lyline.SafetyAlerts.repository.FireStationRepo;
import fr.lyline.SafetyAlerts.repository.FireStationRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FireStationServiceImpl implements FireStationService {
  @Autowired
  FireStationRepo repository;

  public FireStationServiceImpl(FireStationRepoImpl repository) {
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
  public boolean addFireStation(FireStation fireStation) {
    if (fireStation.getStation() != 0 && fireStation.getAddress() != null) {
      boolean result = repository.add(fireStation);
      return result;
    } else return false;
  }

  @Override
  public boolean updateFireStation(Integer oldStationNumber, String address, FireStation fireStationToUpDate) {
    return repository.update(oldStationNumber, address, fireStationToUpDate);
  }

  @Override
  public boolean removeFireStation(String stationNumber, String address) {
    return repository.deleteByStationAndAddress(Integer.valueOf(stationNumber), address);
  }

  public List<FireStation> getAllFireStationsObject() {
    List<FireStation> data = repository.findAll();
    return data;
  }
}
