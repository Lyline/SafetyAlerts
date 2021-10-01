package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.Person;

import java.util.List;

public interface PersonRepo {
  List<Person> findAll();

  Person findById(String firstName, String lastName);

  boolean add(Person person);

  boolean update(Person person);

  boolean deleteByFirstNameAndLastName(String firstName, String lastName);
}
