package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.FireStation;
import fr.lyline.SafetyAlerts.utils.JsonConverter;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class FireStationRepoImpl implements FireStationRepo {

  JsonConverter data = new JsonConverter();
  private Map<String, FireStation> fireStationMap =
      (Map<String, FireStation>) data.convertJsonToObject("src/main/resources/fireStation.json");

  public FireStationRepoImpl() {
  }

  @Override
  public List<FireStation> findAll() {
    return fireStationMap.values().stream().collect(Collectors.toList());
  }

  @Override
  public FireStation findById(String address) {
    return fireStationMap.get(address);
  }

  @Override
  public void add(FireStation fireStation) {
    fireStationMap.put(fireStation.getStation() + "-" + fireStation.getAddress(), fireStation);
  }

  @Override
  public void addAll(List<FireStation> list) {
    HashMap<String, FireStation> subList = new HashMap<>();

    for (FireStation f : list) {
      subList.put(f.getStation() + "-" + f.getAddress(), f);
    }

    fireStationMap.putAll(subList);
  }

  @Override
  public void update(String id, FireStation fireStationToUpDate) {
    fireStationMap.remove(id);
    fireStationMap.put(fireStationToUpDate.getStation() + "-" + fireStationToUpDate.getAddress(), fireStationToUpDate);
  }

  @Override
  public void deleteById(String id) {
    fireStationMap.remove(id);
  }

  @Override
  public void deleteAll() {
    fireStationMap.clear();
  }
}
