package fr.lyline.SafetyAlerts.service;

import com.google.inject.internal.util.ImmutableList;
import fr.lyline.SafetyAlerts.ObjectMapper.ChildAlert;
import fr.lyline.SafetyAlerts.ObjectMapper.PersonInfo;
import fr.lyline.SafetyAlerts.model.FireStation;
import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.model.Person;
import fr.lyline.SafetyAlerts.repository.FireStationRepoImpl;
import fr.lyline.SafetyAlerts.repository.MedicalRecordRepoImpl;
import fr.lyline.SafetyAlerts.repository.PersonRepoImpl;
import org.joda.time.DateTime;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 The implementation Main functions api service. This class manages the endpoints service of the API.

 @author Quesne GC
 @see fr.lyline.SafetyAlerts.repository.PersonRepo
 @see fr.lyline.SafetyAlerts.repository.MedicalRecordRepo
 @see fr.lyline.SafetyAlerts.repository.FireStationRepo
 @since 0.1 */
@Service
public class MainFunctionsAPIService {
  /**
   The Person repository.
   */
  @Autowired
  PersonRepoImpl personRepo;
  /**
   The Fire station repository.
   */
  @Autowired
  FireStationRepoImpl fireStationRepo;
  /**
   The Medical record repository.
   */
  @Autowired
  MedicalRecordRepoImpl medicalRecordRepo;

  /**
   Instantiates a new Main functions api service.

   @param personRepo        the person repo
   @param fireStationRepo   the fire station repo
   @param medicalRecordRepo the medical record repo

   @see fr.lyline.SafetyAlerts.repository.PersonRepo
   @see fr.lyline.SafetyAlerts.repository.MedicalRecordRepo
   @see fr.lyline.SafetyAlerts.repository.FireStationRepo
   */
  public MainFunctionsAPIService(PersonRepoImpl personRepo, FireStationRepoImpl fireStationRepo,
                                 MedicalRecordRepoImpl medicalRecordRepo) {
    this.personRepo = personRepo;
    this.fireStationRepo = fireStationRepo;
    this.medicalRecordRepo = medicalRecordRepo;
  }

  /**
   Gets the list of all community email.

   @param city the city of the community

   @return the list of community email

   @see fr.lyline.SafetyAlerts.repository.PersonRepo
   */
  public Set<String> getCommunityEmail(String city) {
    List<Person> data = personRepo.findAll();

    return data.stream()
        .filter(x -> x.getCity().contains(city))
        .map(Person::getEmail)
        .collect(Collectors.toSet());
  }

  /**
   Gets the list of phone numbers desserve by the fire station entered on parameter.

   @param fireStation_number the fire station number

   @return the list of phone numbers

   @see fr.lyline.SafetyAlerts.repository.FireStationRepo
   @see fr.lyline.SafetyAlerts.repository.PersonRepo
   */
  public Set<String> getPhoneForAlert(Integer fireStation_number) {
    List<FireStation> stationData = fireStationRepo.findAll();
    List<Person> personData = personRepo.findAll();
    Set<String> list = new HashSet<>();

    List<String> stationResponse = stationData.stream()
        .filter(x -> x.getStation() == fireStation_number)
        .map(FireStation::getAddress)
        .collect(Collectors.toList());

    for (String address : stationResponse) {
      for (Person x : personData) {
        if (x.getAddress().equals(address)) {
          list.add(x.getPhone());
        }
      }
    }
    return list;
  }

  /**
   Gets identity and medical information of the person entered in parameter.

   @param firstName the first name
   @param lastName  the last name

   @return the person info object

   @see fr.lyline.SafetyAlerts.repository.PersonRepo
   @see fr.lyline.SafetyAlerts.repository.MedicalRecordRepo
   @see fr.lyline.SafetyAlerts.ObjectMapper.PersonInfo
   */
  public PersonInfo getPersonInfo(String firstName, String lastName) {
    PersonInfo personInfo;

    Person personData = personRepo.findById(firstName, lastName);
    MedicalRecord medicalRecordData = medicalRecordRepo.findByFirstNameAndLastName(firstName, lastName);

    if (personData == null) {
      return null;
    }

    if (personData.getFirstName() != null && medicalRecordData.getFirstName() != null) {
      int age = Years.yearsBetween(medicalRecordData.getBirthdate(), DateTime.now()).getYears();

      personInfo = new PersonInfo(personData.getFirstName(), personData.getLastName(), personData.getPhone(),
          personData.getEmail(), age, medicalRecordData.getMedications(), medicalRecordData.getAllergies());
      return personInfo;
    } else return null;
  }

  /**
   Gets a list of children with their parent identities by child. A child is a person has less or equal 18 years.

   @param address the address

   @return the list of child alert objects

   @see fr.lyline.SafetyAlerts.repository.MedicalRecordRepo
   @see fr.lyline.SafetyAlerts.ObjectMapper.ChildAlert
   */
  public List<ChildAlert> getChildAlert(String address) {
    List<Person> persons = personRepo.findAll();

    List<Person> children = new ArrayList<>();
    List<Person> adults = new ArrayList<>();
    List<String> ages = new ArrayList<>();

    List<ChildAlert> result = new ArrayList<>();

    List<Person> residents = persons.stream()
        .filter(x -> x.getAddress().equals(address))
        .collect(Collectors.toList());

    for (Person resident : residents) {
      MedicalRecord person = medicalRecordRepo.findByFirstNameAndLastName(resident.getFirstName(), resident.getLastName());
      int age = Years.yearsBetween(person.getBirthdate(), DateTime.now()).getYears();

      if (age <= 18) {
        ages.add(String.valueOf(age));
        children.add(resident);
      } else adults.add(resident);
    }

    for (int i = 0; i < children.size(); i++) {
      result.add(new ChildAlert(children.get(i), ages.get(i), adults));
    }
    return result;
  }

  /**
   Gets the list sorted by address of residents with medical information from to fire station list entered in parameter.

   @param stationList the station list

   @return the list of residents contact from to the linked fire station

   @see fr.lyline.SafetyAlerts.repository.PersonRepo
   @see fr.lyline.SafetyAlerts.repository.FireStationRepo
   @see fr.lyline.SafetyAlerts.repository.MedicalRecordRepo
   */
  public Map<Integer, List<Map<String, List<PersonInfo>>>> getResidentsContactFromToFireStation(int[] stationList) {
    List<Person> persons = personRepo.findAll();
    List<FireStation> fireStations = fireStationRepo.findAll();

    Map<Integer, List<Map<String, List<PersonInfo>>>> result = new HashMap<>();

    for (int stationNumber : stationList) {

      //Get a list of addresses from to the station
      List<String> addresses = fireStations.stream()
          .filter(x -> x.getStation() == stationNumber)
          .map(FireStation::getAddress)
          .collect(Collectors.toList());

      Map<String, List<PersonInfo>> residentsByAddress = new HashMap<>();

      //Sort and collect people by address
      for (String address : addresses) {

        List<PersonInfo> residents = new ArrayList<>();

        //Collect people leaving in to this address
        for (Person person : persons) {

          if (person.getAddress().equals(address)) {
            MedicalRecord medic = medicalRecordRepo.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
            PersonInfo personInfo = new PersonInfo().create(person, medic);
            residents.add(personInfo);
          }
        }
        if (!residents.isEmpty()) {
          residentsByAddress.put(address, residents);
        }
      }
      if (!residentsByAddress.isEmpty()) {
        result.put(stationNumber, ImmutableList.of(residentsByAddress));
      }
    }
    return result;
  }

  /**
   Gets the list of person information sorted by address by station and the sum of adults and children from to fire station
   entered in parameter.

   @param stationNumber the station number

   @return the list of person information with the sum of adults and children
   */
  public Map<String, List<Map<String, String>>> getPersonsInfoByStation(int stationNumber) {
    List<Person> personData = personRepo.findAll();
    List<FireStation> stationData = fireStationRepo.findAll();

    List<Map<String, String>> personList = new ArrayList<>();
    List<Map<String, String>> count = new ArrayList<>();
    Map<String, String> countMap = new HashMap<>();

    Map<String, List<Map<String, String>>> informationsList = new HashMap<>();

    int childCount = 0;
    int adultCount = 0;

    List<String> addresses = stationData.stream()
        .filter(x -> x.getStation() == stationNumber)
        .map(FireStation::getAddress)
        .collect(Collectors.toList());

    for (String address : addresses) {
      for (Person person : personData) {
        if (person.getAddress().equals(address)) {
          Map<String, String> personInfoMap = new HashMap<>();

          personInfoMap.put("firstName", person.getFirstName());
          personInfoMap.put("lastName", person.getLastName());
          personInfoMap.put("address", person.getAddress());
          personInfoMap.put("phone", person.getPhone());

          personList.add(personInfoMap);

          MedicalRecord medicalPersonInfo =
              medicalRecordRepo.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
          int agePerson = Years.yearsBetween(medicalPersonInfo.getBirthdate(), DateTime.now()).getYears();

          if (agePerson <= 18) {
            childCount++;
          } else {
            adultCount++;
          }
        }
      }
    }
    countMap.put("children", String.valueOf(childCount));
    countMap.put("adults", String.valueOf(adultCount));
    count.add(countMap);

    if (!personList.isEmpty() && !count.isEmpty()) {
      informationsList.put("persons", personList);
      informationsList.put("count", count);
    }
    return informationsList;
  }

  /**
   Gets the list of all person information, identity and medical, and fire station deserved from to address entered in
   parameter.

   @param address the address

   @return the list of persons information by station number

   @see fr.lyline.SafetyAlerts.repository.PersonRepo
   @see fr.lyline.SafetyAlerts.repository.PersonRepo
   */
  public Map<String, List<Object>> getPersonsInfoByAddress(String address) {
    List<Person> personData = personRepo.findAll();
    List<FireStation> fireStationData = fireStationRepo.findAll();
    Map<String, List<Object>> result = new HashMap<>();
    List<Object> personInfoList = new ArrayList<>();

    List<Person> residentsByAddress = personData.stream()
        .filter(x -> x.getAddress().equals(address))
        .collect(Collectors.toList());

    List<Integer> fireStations = fireStationData.stream()
        .filter(x -> x.getAddress().equals(address))
        .map(FireStation::getStation)
        .collect(Collectors.toList());

    for (Person person : residentsByAddress) {
      Map<String, String> personInfo = new HashMap<>();
      Map<String, Object> resident = new HashMap<>();

      MedicalRecord medicInfo = medicalRecordRepo.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
      String age = String.valueOf(Years.yearsBetween(medicInfo.getBirthdate(), DateTime.now()).getYears());

      personInfo.put("phone", person.getPhone());
      personInfo.put("age", age);
      personInfo.put("medications", Arrays.asList(medicInfo.getMedications()).toString());
      personInfo.put("allergies", Arrays.asList(medicInfo.getAllergies()).toString());

      resident.put("firstName", person.getFirstName());
      resident.put("lastName", person.getLastName());
      resident.put("information", personInfo);

      if (!personInfo.isEmpty() && !resident.isEmpty()) {
        personInfoList.add(resident);
      }
    }
    if (!personInfoList.isEmpty()) {
      result.put("firestation", List.of(fireStations));
      result.put("persons", personInfoList);
    }
    return result;
  }
}
