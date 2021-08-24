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
    if (persons != null) {
      return new ResponseEntity<>(persons, HttpStatus.OK);
    } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  @GetMapping(value = "/person/{firstName}_{lastName}")
  public ResponseEntity<Person> getPerson(@PathVariable(value = "firstName") String firstName,
                                          @PathVariable(value = "lastName") String lastName) {
    Person person = service.getPerson(firstName + lastName);

    if (person != null) {
      return new ResponseEntity<>(person, HttpStatus.FOUND);
    } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  @PostMapping("/person")
  public ResponseEntity<Person> addPerson(@RequestBody Person person) {
    Person personIsPresent = service.getPerson(person.getFirstName() + person.getLastName());

    if (personIsPresent == null) {
      Person newPerson = service.addPerson(person);
      return new ResponseEntity<>(newPerson, HttpStatus.CREATED);
    } else return new ResponseEntity<>(personIsPresent, HttpStatus.CONFLICT);
  }

  @PatchMapping("/person/{firstName}_{lastName}")
  public ResponseEntity<Person> updatePerson(@PathVariable(value = "firstName") String firstName,
                                             @PathVariable(value = "lastName") String lastName,
                                             @RequestBody Person personToUpDate) {

    String id = firstName + lastName;
    Person personSaved = service.getPerson(id);

    if (personSaved != null) {
      Person personUpDated = service.upDatePerson(id, personToUpDate);
      return new ResponseEntity<>(personUpDated, HttpStatus.OK);
    } else return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
  }

  @DeleteMapping("/person/{firstName}_{lastName}")
  public ResponseEntity deletePerson(@PathVariable(value = "firstName") String firstName,
                                     @PathVariable(value = "lastName") String lastName) {

    Person personToDelete = service.getPerson(firstName + lastName);

    if (personToDelete != null) {
      service.removePerson(firstName + lastName);
      return new ResponseEntity(HttpStatus.OK);
    } else return new ResponseEntity(HttpStatus.NO_CONTENT);

  }

}
