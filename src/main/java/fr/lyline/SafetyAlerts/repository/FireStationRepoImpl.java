package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.FireStation;
import fr.lyline.SafetyAlerts.utils.JsonConverter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 The implementation of fire station repository for json data.

 @author Quesne GC
 @see fr.lyline.SafetyAlerts.repository.FireStationRepo
 @since 0.1 */
@Repository
public class FireStationRepoImpl implements FireStationRepo {

  JsonConverter data;
  String fileJsonPath = "src/main/resources/fireStation.json";

  /**
   Instantiates a new Fire station repository.

   @param data the fire station object data
   */
  public FireStationRepoImpl(JsonConverter data) {
    this.data = data;
  }

  @Override
  public List<FireStation> findAll() {
    List<FireStation> stationDataList = (List<FireStation>) data.convertJsonToObject(fileJsonPath);
    return new ArrayList<>(stationDataList);
  }

  @Override
  public FireStation findByStationNumberAndAddress(Integer stationNumber, String address) {
    List<FireStation> stationDataList = (List<FireStation>) data.convertJsonToObject(fileJsonPath);
    for (FireStation station : stationDataList) {
      if (station.getStation() == stationNumber && station.getAddress().equals(address)) {
        return station;
      }
    }
    return null;
  }

  @Override
  public boolean add(FireStation fireStation) {
    List<FireStation> stationDataList = (List<FireStation>) data.convertJsonToObject(fileJsonPath);

    if (!stationDataList.contains(fireStation)) {
      if ((fireStation.getStation() != null) &&
          fireStation.getAddress() != null) {
        stationDataList.add(fireStation);
        data.convertObjectToJson(fileJsonPath, stationDataList);
        return true;
      } else return false;
    } else return false;
  }

  @Override
  public boolean update(Integer oldStationNumber, String oldAddress, FireStation fireStationToUpDate) {
    List<FireStation> stationDataList = (List<FireStation>) data.convertJsonToObject(fileJsonPath);
    FireStation oldStation = null;

    for (FireStation stationSearch : stationDataList) {
      if (stationSearch.getStation() == oldStationNumber && stationSearch.getAddress().equals(oldAddress)) {
        oldStation = stationSearch;
      }
    }
    //if old station exists then update to fireStationToUpdate
    if (oldStation != null) {

      //if the update station already exist then fireStationToUpdate not use and the old station delete
      //for not create a same entity
      FireStation stationTest = findStation(fireStationToUpDate);
      if (stationTest != null) {
        stationDataList.remove(oldStation);
        data.convertObjectToJson(fileJsonPath, stationDataList);
        return true;
      } else {
        //if fireStationToUpdate not already exist then replace it
        stationDataList.remove(oldStation);
        stationDataList.add(fireStationToUpDate);
        data.convertObjectToJson(fileJsonPath, stationDataList);
        return true;
      }
    } else return false;
  }

  @Override
  public boolean deleteByStationAndAddress(Integer stationNumber, String address) {
    List<FireStation> stationDataList = (List<FireStation>) data.convertJsonToObject(fileJsonPath);
    FireStation stationToDelete = null;

    for (FireStation fs : stationDataList) {
      if (fs.getStation() == stationNumber && fs.getAddress().equals(address)) {
        stationToDelete = fs;
      }
    }

    if (stationToDelete != null) {
      stationDataList.remove(stationToDelete);
      data.convertObjectToJson(fileJsonPath, stationDataList);
      return true;
    } else return false;
  }

  private FireStation findStation(FireStation fireStation) {
    List<FireStation> stationDataList = (List<FireStation>) data.convertJsonToObject(fileJsonPath);
    FireStation stationSearch = null;

    for (FireStation fs : stationDataList) {
      if (fs.getStation() == fireStation.getStation() &&
          fs.getAddress().equals(fireStation.getAddress())) {
        stationSearch = fireStation;
      }
    }
    return stationSearch;
  }
}
