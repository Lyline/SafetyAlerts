package fr.lyline.SafetyAlerts.repository;

import fr.lyline.SafetyAlerts.model.Person;

import java.util.List;

/**
 The interface of person repository.

 @author Quesne GC
 @since 0.1 */
public interface PersonRepo {
  /**
   Find all persons.

   @return the list of persons
   */
  List<Person> findAll();

  /**
   Find a person by the id person, his first name and his last name.

   @param firstName the first name of this person
   @param lastName  the last name of this person

   @return the person
   */
  Person findById(String firstName, String lastName);

  /**
   Add a new person in data.

   @param person the person

   @return boolean, true if the add validate else false
   */
  boolean add(Person person);

  /**
   Update this person.

   @param person the person

   @return boolean, true if the update validate else false
   */
  boolean update(Person person);

  /**
   Delete a person by first name and last name.

   @param firstName the first name of this person
   @param lastName  the last name of this person

   @return boolean, true if the deletion validate else false
   */
  boolean deleteByFirstNameAndLastName(String firstName, String lastName);
}
