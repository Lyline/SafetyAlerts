package fr.lyline.SafetyAlerts.service;

import fr.lyline.SafetyAlerts.model.Person;
import fr.lyline.SafetyAlerts.repository.PersonRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
  @Autowired
  PersonRepoImpl repository;

  public PersonServiceImpl(PersonRepoImpl repository) {
    this.repository = repository;
  }

  @Override
  public Person getPerson(String id) {
    return repository.findById(id);
  }

  @Override
  public List<Person> getAllPersons() {
    return repository.findAll();
  }

  @Override
  public Person addPerson(Person person) {
    if (person.getFirstName() != null && person.getLastName() != null &&
        person.getAddress() != null && person.getCity() != null &&
        person.getZip() != 0 && person.getPhone() != null)
      repository.add(person);
    return repository.findById(person.getFirstName() + person.getLastName());
  }

  @Override
  public void addAllPersons(List<Person> list) {
    for (Person p : list) {
      addPerson(p);
    }
  }

  @Override
  public Person upDatePerson(String id, Person personToUpDate) {
    Person person = repository.findById(id);

    if (person != null) {
      if (personToUpDate.getAddress() != null) {
        person.setAddress(personToUpDate.getAddress());
      }
      if (personToUpDate.getCity() != null) {
        person.setCity(personToUpDate.getCity());
      }
      if (personToUpDate.getZip() != 0) {
        person.setZip(personToUpDate.getZip());
      }
      if (personToUpDate.getPhone() != null) {
        person.setPhone(personToUpDate.getPhone());
      }
      if (personToUpDate.getEmail() != null) {
        person.setEmail(personToUpDate.getEmail());
      }
      repository.update(id, person);
    }
    return person;
  }

  @Override
  public void removePerson(String id) {
    repository.deleteById(id);
  }

  @Override
  public void removeAllPersons() {
    repository.deleteAll();
  }
}
