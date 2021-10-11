package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.FireStation;

import java.util.List;

/**
 The interface of fire station service.

 @author Quesne GC
 @see fr.lyline.SafetyAlerts.repository.FireStationRepo
 @since 0.1 */
public interface FireStationService {
  /**
   Gets a list of fire station from to an address.

   @param address the intervention address

   @return the fire station list

   @see fr.lyline.SafetyAlerts.repository.FireStationRepo
   */
  List<Integer> getFireStation(String address);

  /**
   Find all fire station's number from to repository.

   @return the list of fire station numbers

   @see fr.lyline.SafetyAlerts.repository.FireStationRepo
   */
  List<Integer> getAllFireStations();

  /**
   Add a new fire station from to repository.

   @param fireStation the fire station to add

   @return boolean, true if the addition's repository method return true, else false

   @see fr.lyline.SafetyAlerts.repository.FireStationRepo
   */
  boolean addFireStation(FireStation fireStation);

  /**
   Update this fire station from to repository.

   @param oldStationNumber    the old station number
   @param address             the intervention address
   @param fireStationToUpDate the fire station object to update

   @return boolean, true, if the update's repository method return true, else false

   @see fr.lyline.SafetyAlerts.repository.FireStationRepo
   */
  boolean updateFireStation(Integer oldStationNumber, String address, FireStation fireStationToUpDate);

  /**
   Delete this fire station object from to repository.

   @param stationNumber the fire station's number of this station to delete
   @param address       the intervention address of this station to delete

   @return boolean, true if the deletion's repository method return true, else false

   @see fr.lyline.SafetyAlerts.repository.FireStationRepo
   */
  boolean removeFireStation(String stationNumber, String address);
}
