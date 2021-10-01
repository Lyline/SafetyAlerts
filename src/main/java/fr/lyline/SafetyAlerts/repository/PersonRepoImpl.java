package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.Person;
import fr.lyline.SafetyAlerts.utils.JsonConverter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepoImpl implements PersonRepo {

  JsonConverter data;
  String fileJsonPath = "src/main/resources/person.json";

  public PersonRepoImpl(JsonConverter data) {
    this.data = data;
  }

  @Override
  public List<Person> findAll() {
    List<Person> personDataList = (List<Person>) data.convertJsonToObject(fileJsonPath);
    return new ArrayList<>(personDataList);
  }

  @Override
  public Person findById(String firstName, String lastName) {
    List<Person> personDataList = (List<Person>) data.convertJsonToObject(fileJsonPath);
    for (Person person : personDataList) {
      if (person.getFirstName().equals(firstName) &&
          person.getLastName().equals(lastName)) {
        return person;
      }
    }
    return null;
  }

  @Override
  public boolean add(Person person) {
    List<Person> personDataList = (List<Person>) data.convertJsonToObject(fileJsonPath);
    Person personIsExist = null;

    for (Person p : personDataList) {
      if (p.getFirstName().equals(person.getFirstName())) {
        personIsExist = p;
      }
    }

    if (personIsExist == null) {
      personDataList.add(person);
      data.convertObjectToJson(fileJsonPath, personDataList);
      return true;
    } else return false;
  }

  @Override
  public boolean update(Person person) {
    ArrayList<Person> personDataList = (ArrayList<Person>) data.convertJsonToObject(fileJsonPath);
    Person personIsExist = null;

    for (Person p : personDataList) {
      if (p.getFirstName().equals(person.getFirstName())) {
        personIsExist = p;
      }
    }

    if (personIsExist != null) {
      personDataList.remove(personIsExist);
      personDataList.add(person);

      data.convertObjectToJson(fileJsonPath, personDataList);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean deleteByFirstNameAndLastName(String firstName, String lastName) {
    List<Person> personDataList = (List<Person>) data.convertJsonToObject(fileJsonPath);
    Person personToDelete = null;

    for (Person p : personDataList) {
      if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
        personToDelete = p;
      }
    }
    if (personToDelete != null) {
      personDataList.remove(personToDelete);
      data.convertObjectToJson(fileJsonPath, personDataList);
      return true;
    } else {
      return false;
    }
  }
}
