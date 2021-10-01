package fr.lyline.SafetyAlerts.controller;

import fr.lyline.SafetyAlerts.model.Person;
import fr.lyline.SafetyAlerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

  @Autowired
  PersonService service;

  @GetMapping("/persons")
  public ResponseEntity<List<Person>> getPersons() {
    List<Person> persons = service.getAllPersons();
    if (!persons.isEmpty()) {
      return new ResponseEntity<>(persons, HttpStatus.OK);
    } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  @GetMapping("/persons/{firstName}_{lastName}")
  public ResponseEntity<Person> getPerson(@PathVariable() String firstName,
                                          @PathVariable() String lastName) {
    Person response = service.getPerson(firstName, lastName);

    if (response != null) {
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  @PostMapping("/persons")
  public ResponseEntity<Boolean> addPerson(@RequestBody Person person) {

    Person personIsPresent = service.getPerson(person.getFirstName(), person.getLastName());

    if (personIsPresent == null) {
      boolean response = service.addPerson(person);
      if (response) {
        return new ResponseEntity<>(HttpStatus.CREATED);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } else return new ResponseEntity<>(HttpStatus.CONFLICT);
  }

  @PatchMapping("/persons/{firstName}_{lastName}")
  public ResponseEntity<Person> updatePerson(@PathVariable(value = "firstName") String firstName,
                                             @PathVariable(value = "lastName") String lastName,
                                             @RequestBody Person personToUpDate) {

    Person personSaved = service.getPerson(firstName, lastName);

    if (personSaved != null) {
      service.upDatePerson(personToUpDate);

      return new ResponseEntity<>(HttpStatus.OK);
    } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/persons/{firstName}_{lastName}")
  public ResponseEntity deletePerson(@PathVariable(value = "firstName") String firstName,
                                     @PathVariable(value = "lastName") String lastName) {

    Person personToDelete = service.getPerson(firstName, lastName);

    if (personToDelete != null) {
      service.removePerson(firstName, lastName);
      return new ResponseEntity(HttpStatus.OK);
    } else return new ResponseEntity(HttpStatus.NOT_FOUND);

  }

}
