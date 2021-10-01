package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.Person;

import java.util.List;

public interface PersonService {
  Person getPerson(String firstName, String lastName);

  List<Person> getAllPersons();

  boolean addPerson(Person person);

  boolean upDatePerson(Person personToUpDate);

  boolean removePerson(String firstName, String lastName);

}
