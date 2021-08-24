package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.Person;

import java.util.List;

public interface PersonService {
  Person getPerson(String id);

  List<Person> getAllPersons();

  Person addPerson(Person person);

  void addAllPersons(List<Person> list);

  Person upDatePerson(String id, Person personToUpDate);

  void removePerson(String id);

  void removeAllPersons();
}
