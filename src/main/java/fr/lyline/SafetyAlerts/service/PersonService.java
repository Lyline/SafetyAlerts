package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.Person;

import java.util.List;

/**
 The interface of person service.

 @author Quesne GC
 @see fr.lyline.SafetyAlerts.repository.PersonRepo
 @since 0.1 */
public interface PersonService {
  /**
   Gets a person from to repository.

   @param firstName the first name of this person
   @param lastName  the last name of this person

   @return the person

   @see fr.lyline.SafetyAlerts.repository.PersonRepo
   */
  Person getPerson(String firstName, String lastName);

  /**
   Gets all persons from to repository.

   @return the list of all persons

   @see fr.lyline.SafetyAlerts.repository.PersonRepo
   */
  List<Person> getAllPersons();

  /**
   Add a new person from to repository.

   @param person the person to add

   @return boolean, true if the add method of repository return true, else false

   @see fr.lyline.SafetyAlerts.repository.PersonRepo
   */
  boolean addPerson(Person person);

  /**
   Update a person from to repository.

   @param personToUpDate the person to update

   @return boolean, true if the update method of repository return true, else false

   @see fr.lyline.SafetyAlerts.repository.PersonRepo
   */
  boolean upDatePerson(Person personToUpDate);

  /**
   Remove a person from to repository.

   @param firstName the first name of this person to delete
   @param lastName  the last name of this person to delete

   @return boolean, true if the delete method of repository return true, else false

   @see fr.lyline.SafetyAlerts.repository.PersonRepo
   */
  boolean removePerson(String firstName, String lastName);

}
