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

@RestController
public class PersonController {

  @Autowired
  PersonService service;

  Logger logger = LogManager.getLogger(PersonController.class);

  @GetMapping("/persons")
  public ResponseEntity<List<Person>> getPersons() {
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
