package fr.lyline.SafetyAlerts.service;

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


@Service
public class MainFunctionsAPIService {
  @Autowired
  PersonRepoImpl personRepo;
  @Autowired
  FireStationRepoImpl fireStationRepo;
  @Autowired
  MedicalRecordRepoImpl medicalRecordRepo;

  public MainFunctionsAPIService(PersonRepoImpl personRepo, FireStationRepoImpl fireStationRepo,
                                 MedicalRecordRepoImpl medicalRecordRepo) {
    this.personRepo = personRepo;
    this.fireStationRepo = fireStationRepo;
    this.medicalRecordRepo = medicalRecordRepo;
  }

  public Set<String> getCommunityEmail(String city) {
    List<Person> data = personRepo.findAll();

    return data.stream()
        .filter(x -> x.getCity().contains(city))
        .map(Person::getEmail)
        .collect(Collectors.toSet());
  }

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

  public Map<String, String> getPersonInfo(String firstName, String lastName) {

    Map<String, String> personInfo;
    Person personData = personRepo.findById(firstName + lastName);
    MedicalRecord medicalRecordData = medicalRecordRepo.findById(firstName + lastName);
    personInfo = new HashMap<>();

    if (personData != null && medicalRecordData != null) {
      String age = String.valueOf(Years.yearsBetween(medicalRecordData.getBirthdate(), DateTime.now()).getYears());

      personInfo.put("firstName", personData.getFirstName());
      personInfo.put("lastName", personData.getLastName());
      personInfo.put("age", age);
      personInfo.put("email", personData.getEmail());
      personInfo.put("medications", Arrays.asList(medicalRecordData.getMedications()).toString());
      personInfo.put("allergies", Arrays.asList(medicalRecordData.getAllergies()).toString());
    }
    return personInfo;
  }

  public Map<String, List<HashMap<String, String>>> getChildAlert(String address) {
    List<Person> persons = personRepo.findAll();
    List<MedicalRecord> medicalRecordResidents = new ArrayList<>();

    List<HashMap<String, String>> childrenList = new ArrayList<>();
    List<HashMap<String, String>> adultList = new ArrayList<>();
    Map<String, List<HashMap<String, String>>> family = new HashMap<>();

    List<Person> residents = persons.stream()
        .filter(x -> x.getAddress().equals(address))
        .collect(Collectors.toList());

    for (Person p : residents) {
      MedicalRecord resident = medicalRecordRepo.findById(p.getFirstName() + p.getLastName());
      medicalRecordResidents.add(resident);
    }

    List<MedicalRecord> children = medicalRecordResidents.stream()
        .filter(x -> Years.yearsBetween(x.getBirthdate(), DateTime.now()).getYears() <= 18)
        .collect(Collectors.toList());

    List<MedicalRecord> adults = medicalRecordResidents.stream()
        .filter(x -> Years.yearsBetween(x.getBirthdate(), DateTime.now()).getYears() > 18)
        .collect(Collectors.toList());

    for (MedicalRecord child : children) {
      HashMap<String, String> childInfo = new HashMap<>();
      String age = String.valueOf(Years.yearsBetween(child.getBirthdate(), DateTime.now()).getYears());

      childInfo.put("firstName", child.getFirstName());
      childInfo.put("lastName", child.getLastName());
      childInfo.put("age", age);

      childrenList.add(childInfo);
    }
    family.put("children", childrenList);

    for (MedicalRecord adult : adults) {
      HashMap<String, String> adultInfo = new HashMap<>();

      adultInfo.put("firstName", adult.getFirstName());
      adultInfo.put("lastName", adult.getLastName());

      adultList.add(adultInfo);
    }
    family.put("adults", adultList);

    return family;
  }

  public Map<Integer, Map<String, List<Map<String, String>>>> getResidentsByAddressFromFireStationList(int[] stationList) {
    List<FireStation> stationData = fireStationRepo.findAll();
    List<Person> personData = personRepo.findAll();

    List<String> addresses;
    Map<String, List<Map<String, String>>> residents = new HashMap<>();
    Map<Integer, Map<String, List<Map<String, String>>>> addressesByStation = new HashMap<>();

    for (int i = 0; i < stationList.length; i++) {
      int finalI = i;
      addresses = stationData.stream()
          .filter(x -> x.getStation() == stationList[finalI])
          .map(FireStation::getAddress)
          .collect(Collectors.toList());

      for (String address : addresses) {
        List<Map<String, String>> personList = new ArrayList<>();

        for (Person person : personData) {
          Map<String, String> personMap = new HashMap<>();
          if (person.getAddress().equals(address)) {
            personMap.put("firstName", person.getFirstName());
            personMap.put("lastName", person.getLastName());
            personMap.put("phone", person.getPhone());
            personList.add(personMap);
          }
          if (!personMap.isEmpty()) {
            residents.put(person.getAddress(), personList);
          }
        }
        addressesByStation.put(stationList[finalI], residents);
      }
    }

    return addressesByStation;
  }

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

          MedicalRecord medicalPersonInfo = medicalRecordRepo.findById(person.getFirstName() + person.getLastName());
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

    informationsList.put("persons", personList);
    informationsList.put("count", count);
    return informationsList;
  }

  public Map<String, Object> getPersonsInfoByAddress(String address) {
    List<Person> personData = personRepo.findAll();
    List<FireStation> fireStationData = fireStationRepo.findAll();
    Map<String, Object> result = new HashMap<>();
    List<Object> personInfoList = new ArrayList<>();

    List<Person> residentsByAddress = personData.stream()
        .filter(x -> x.getAddress().equals(address))
        .collect(Collectors.toList());

    List<Integer> fireStation = fireStationData.stream()
        .filter(x -> x.getAddress().equals(address))
        .map(FireStation::getStation)
        .collect(Collectors.toList());

    for (Person person : residentsByAddress) {
      Map<String, String> personInfo = new HashMap<>();
      Map<String, Object> resident = new HashMap<>();

      MedicalRecord medicInfo = medicalRecordRepo.findById(person.getFirstName() + person.getLastName());
      String age = String.valueOf(Years.yearsBetween(medicInfo.getBirthdate(), DateTime.now()).getYears());

      personInfo.put("phone", person.getPhone());
      personInfo.put("age", age);
      personInfo.put("medications", Arrays.asList(medicInfo.getMedications()).toString());
      personInfo.put("allergies", Arrays.asList(medicInfo.getAllergies()).toString());

      resident.put("firstName", person.getFirstName());
      resident.put("lastName", person.getLastName());
      resident.put("information", personInfo);

      personInfoList.add(resident);
    }
    result.put("firestation", fireStation);
    result.put("persons", personInfoList);

    return result;
  }
}
