package fr.lyline.SafetyAlerts.controller;

import fr.lyline.SafetyAlerts.model.Person;
import fr.lyline.SafetyAlerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 The Person controller of the CRUD application.

 @author Quesne GC
 @see fr.lyline.SafetyAlerts.service.PersonService
 @since 0.1 */
@RestController
public class PersonController {

  /**
   The interface of peron service.

   @see fr.lyline.SafetyAlerts.service.PersonService
   */
  @Autowired
  PersonService service;

  /**
   The Logger.

   @see org.apache.logging.log4j.LogManager;
   @see org.apache.logging.log4j.Logger;
   */
  Logger logger = LogManager.getLogger(PersonController.class);

  /**
   Gets a list of persons identity and a HttpStatus 200 when the person service return its response, else its return an
   empty list
   and a HttpStatus 404.

   @return the list of persons

   @see fr.lyline.SafetyAlerts.service.PersonService
   */
  @GetMapping("/persons")
  public ResponseEntity<List<Person>> getAllPersons() {
    List<Person> persons = service.getAllPersons();

    if (!persons.isEmpty()) {
      logger.info("GET /persons : " + persons.size() + " person(s) - Status 200");
      logger.info(persons);
      return new ResponseEntity<>(persons, HttpStatus.OK);
    } else {
      logger.info("GET /persons : No Data - Status 404");
      logger.info(persons.toString());
      return new ResponseEntity<>(persons, HttpStatus.NOT_FOUND);
    }
  }

  /**
   Gets a person identity when the first name and the last name entered in parameter. The person
   service return its response and a HttpStatut 200, else its return an empty list and a HttpStatus 404.

   @param firstName the first name of this person
   @param lastName  the last name of this person

   @return Person object
   */
  @GetMapping("/persons/{firstName}_{lastName}")
  public ResponseEntity<Person> getPerson(@PathVariable() String firstName,
                                          @PathVariable() String lastName) {
    Person response = service.getPerson(firstName, lastName);

    if (response != null) {
      logger.info("GET /persons/" + firstName + "_" + lastName + " - Status 200");
      logger.info(response.toString());
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      logger.warn("GET /persons/" + firstName + "_" + lastName + " - Status 404");
      logger.info("null");
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
  }

  /**
   Add a new person when the request body of Person entered in parameter. Its return a Person object and a HttpStatus 201
   if the service gets true, else if its return an uncompleted Person object then this method return null and a HttpStatus
   404, or else this person already exist then this method return null and a HttpStatus 409.

   @param person the person

   @return the added person
   */
  @PostMapping("/persons")
  public ResponseEntity<Person> addPerson(@RequestBody Person person) {
    Person personIsPresent = service.getPerson(person.getFirstName(), person.getLastName());

    if (personIsPresent == null) {
      boolean response = service.addPerson(person);
      if (response) {
        logger.info("POST /persons : " + person.getFirstName() + " " + person.getLastName() + " - Status 201");
        logger.info(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
      } else {
        logger.warn("POST /persons : " + person.getFirstName() + " " + person.getLastName() +
            " - Incomplete informations for creation - Status 404");
        logger.info(person.toString());
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
    } else {
      logger.warn("POST /persons : " + person.getFirstName() + " " + person.getLastName() +
          " - Person already exist - Status 409");
      logger.info("null");
      return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }
  }

  /**
   Update person entity when the first name and the last name entered in parameters. Its return the updated person and a
   HttpStatus 200 if the service return this person is found, else its return the person not updated and a HttpStatus 404.

   @param firstName      the first name of searched person
   @param lastName       the last name of searched person
   @param personToUpDate the person to update

   @return the person updated
   */
  @PatchMapping("/persons/{firstName}_{lastName}")
  public ResponseEntity<Person> updatePerson(@PathVariable(value = "firstName") String firstName,
                                             @PathVariable(value = "lastName") String lastName,
                                             @RequestBody Person personToUpDate) {
    Person personSaved = service.getPerson(firstName, lastName);

    if (personSaved != null) {
      service.upDatePerson(personToUpDate);
      logger.info("PATCH /persons/" + personSaved.getFirstName() + "_" + personSaved.getLastName() + " - Status 200");
      logger.info("Exist : " + personSaved + "\nUpdate : " + personToUpDate.toString());
      return new ResponseEntity<>(personToUpDate, HttpStatus.OK);
    } else {
      logger.warn("PATCH /persons/" + firstName + "_" + lastName + " - Person not exist - Status 404");
      logger.info(personSaved);
      return new ResponseEntity<>(personSaved, HttpStatus.NOT_FOUND);
    }
  }

  /**
   Delete a person when the first name and the last name of this person entered in parameters. If the service found this
   person then its return th removed person and HttpStatus 200, else its return null and a HttpStatus 404.

   @param firstName the first name of the person to delete
   @param lastName  the last name of the person to delete

   @return the person deleted
   */
  @DeleteMapping("/persons/{firstName}_{lastName}")
  public ResponseEntity deletePerson(@PathVariable(value = "firstName") String firstName,
                                     @PathVariable(value = "lastName") String lastName) {
    Person personToDelete = service.getPerson(firstName, lastName);

    if (personToDelete != null) {
      service.removePerson(firstName, lastName);
      logger.info("DELETE /persons/" + personToDelete.getFirstName() + "_" + personToDelete.getLastName() +
          " - Status 200");
      logger.info(personToDelete.toString());
      return new ResponseEntity(personToDelete, HttpStatus.OK);
    } else {
      logger.warn("DELETE /persons/" + firstName + "_" + lastName +
          " - Person not exist - Status 404");
      logger.info("null");
      return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }
  }
}
