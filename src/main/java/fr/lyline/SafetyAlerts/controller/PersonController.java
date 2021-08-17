package fr.lyline.SafetyAlerts.controller;

import fr.lyline.SafetyAlerts.model.Person;
import fr.lyline.SafetyAlerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

  @Autowired
  PersonService service;


  @GetMapping("/persons")
  public Iterable<Person> getPersons() {
    return service.getAllPersons();
  }

  @GetMapping(value = "/person/{firstName}_{lastName}")
  public Person getPerson(@PathVariable(value = "firstName") String firstName,
                          @PathVariable(value = "lastName") String lastName) {
    Person person = service.getPerson(firstName + lastName);

    if (person != null) {
      return person;
    } else return null;
  }

  @PostMapping("/person")
  public void addPerson(@RequestBody Person person) {
    Person personIsPresent = service.getPerson(person.getFirstName() + person.getLastName());

    if (personIsPresent == null) {
      service.addPerson(person);
    }
  }

  @PutMapping("/person/{firstName}_{lastName}")
  public void updatePerson(@PathVariable(value = "firstName") String firstName,
                           @PathVariable(value = "lastName") String lastName,
                           @RequestBody Person personToUpDate) {

    String id = firstName + lastName;
    Person personSaved = service.getPerson(id);

    if (personSaved != null) {
      service.upDatePerson(id, personToUpDate);
    }
  }

  @DeleteMapping("/person/{firstName}_{lastName}")
  public void deletePerson(@PathVariable(value = "firstName") String firstName,
                           @PathVariable(value = "lastName") String lastName) {

    service.removePerson(firstName + lastName);
  }

}
