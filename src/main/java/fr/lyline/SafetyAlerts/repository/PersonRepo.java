package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.Person;

import java.util.List;

public interface PersonRepo {
  List<Person> findAll();

  Person findById(String firstNameLastName);

  Person add(Person person);

  void addAll(List<Person> list);

  boolean update(String id, Person personToUpDate);

  void deleteById(String id);

  void deleteAll();
}
