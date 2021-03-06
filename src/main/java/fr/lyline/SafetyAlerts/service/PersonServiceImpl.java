package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.Person;
import fr.lyline.SafetyAlerts.repository.PersonRepo;
import fr.lyline.SafetyAlerts.repository.PersonRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 The implementation of person service.

 @author Quesne GC
 @see fr.lyline.SafetyAlerts.repository.PersonRepo
 @since 0.1 */
@Service
public class PersonServiceImpl implements PersonService {
  @Autowired
  PersonRepo repository;

  /**
   Instantiates a new Person service.

   @param repository the person repository

   @see fr.lyline.SafetyAlerts.repository.PersonRepo
   */
  public PersonServiceImpl(PersonRepoImpl repository) {
    this.repository = repository;
  }

  @Override
  public Person getPerson(String firstName, String lastName) {
    return repository.findById(firstName, lastName);
  }

  @Override
  public List<Person> getAllPersons() {
    return repository.findAll();
  }

  @Override
  public boolean addPerson(Person person) {
    if (person.getFirstName() == null | person.getLastName() == null |
        person.getAddress() == null | person.getCity() == null | person.getZip() == 0 |
        person.getPhone() == null | person.getEmail() == null) {
      return false;
    }

    if (!person.getFirstName().isEmpty() && !person.getLastName().isEmpty() &&
        !person.getAddress().isEmpty() && !person.getCity().isEmpty() &&
        person.getZip() != 0 && !person.getPhone().isEmpty()) {
      repository.add(person);
      return true;
    } else return false;
  }

  @Override
  public boolean upDatePerson(Person personToUpDate) {
    Person person = repository.findById(personToUpDate.getFirstName(), personToUpDate.getLastName());

    if (person != null) {
      if (!person.getAddress().equals(personToUpDate.getAddress())) {
        person.setAddress(personToUpDate.getAddress());
      }
      if (!person.getCity().equals(personToUpDate.getCity())) {
        person.setCity(personToUpDate.getCity());
      }
      if (person.getZip() != personToUpDate.getZip()) {
        person.setZip(personToUpDate.getZip());
      }
      if (!person.getPhone().equals(personToUpDate.getPhone())) {
        person.setPhone(personToUpDate.getPhone());
      }
      if (!person.getEmail().equals(personToUpDate.getEmail())) {
        person.setEmail(personToUpDate.getEmail());
      }
      repository.update(person);
      return true;
    } else return false;
  }

  @Override
  public boolean removePerson(String firstName, String lastName) {
    Person person = repository.findById(firstName, lastName);

    if (person != null) {
      repository.deleteByFirstNameAndLastName(firstName, lastName);
      return true;
    } else return false;
  }

}
