package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.Person;
import fr.lyline.SafetyAlerts.utils.JsonConverter;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PersonRepoImpl implements PersonRepo {
  JsonConverter data = new JsonConverter();
  private final Map<String, Person> personMap = (Map<String, Person>) data.convertJsonToObject("src/main/resources/person.json");

  public PersonRepoImpl() {
  }

  @Override
  public List<Person> findAll() {
    return personMap.values().stream().collect(Collectors.toList());
  }

  @Override
  public Person findById(String firstNameLastName) {
    return personMap.get(firstNameLastName);
  }

  @Override
  public Person add(Person person) {
    personMap.put(person.getFirstName() + person.getLastName(), person);
    return personMap.get(person.getFirstName() + person.getLastName());
  }

  @Override
  public void addAll(List<Person> list) {
    HashMap<String, Person> subList = new HashMap<>();

    for (Person p : list) {
      subList.put(p.getFirstName() + p.getLastName(), p);
    }

    personMap.putAll(subList);
  }

  @Override
  public boolean update(String id, Person personToUpDate) {
    Person person = findById(id);
    return personMap.replace(id, person, personToUpDate);
  }

  @Override
  public void deleteById(String id) {
    personMap.remove(id);
  }

  @Override
  public void deleteAll() {
    personMap.clear();
  }
}
