package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.FireStation;

import java.util.List;

/**
 The interface of fire station repository.

 @author Quesne GC
 @since 0.1 */
public interface FireStationRepo {
  /**
   Find all fire stations.

   @return the list of all fire stations
   */
  List<FireStation> findAll();

  /**
   Find a fire station object by its station number and its address.

   @param stationNumber the station number
   @param address       the intervention address

   @return the fire station object
   */
  FireStation findByStationNumberAndAddress(Integer stationNumber, String address);

  /**
   Add a new fire station in data.

   @param fireStation the fire station object

   @return boolean ,true if the add validate else false
   */
  boolean add(FireStation fireStation);

  /**
   Update this fire station.

   @param oldStationNumber    the old station number
   @param oldAddress          the old intervention address
   @param fireStationToUpdate the new fire station object

   @return boolean, true if the update validate else false
   */
  boolean update(Integer oldStationNumber, String oldAddress, FireStation fireStationToUpdate);

  /**
   Delete this fire station object by its station number and its address.

   @param stationNumber the station number
   @param address       the intervention address

   @return boolean, true if the deletion validate else false
   */
  boolean deleteByStationAndAddress(Integer stationNumber, String address);
}
