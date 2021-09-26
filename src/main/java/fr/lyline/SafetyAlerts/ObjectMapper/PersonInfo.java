package fr.lyline.SafetyAlerts.ObjectMapper;

import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.model.Person;
import org.joda.time.DateTime;
import org.joda.time.Years;

public class PersonInfo {
  String firstName;
  String lastName;
  String phone;
  String email;
  int age;
  String[] medications;
  String[] allergies;

  public PersonInfo() {
  }

  public PersonInfo(String firstName, String lastName, String phone,
                    int age, String[] medications, String[] allergies) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.age = age;
    this.medications = medications;
    this.allergies = allergies;
  }

  public PersonInfo(String firstName, String lastName, String phone, String email, int age, String[] medications,
                    String[] allergies) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.email = email;
    this.age = age;
    this.medications = medications;
    this.allergies = allergies;
  }

  public PersonInfo add(Person person, MedicalRecord medic) {
    int age = Years.yearsBetween(medic.getBirthdate(), DateTime.now()).getYears();

    return new PersonInfo(person.getFirstName(), person.getLastName(), person.getPhone(), person.getEmail(), age,
        medic.getMedications(), medic.getAllergies());
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPhone() {
    return phone;
  }

  public int getAge() {
    return age;
  }

  public String[] getMedications() {
    return medications;
  }

  public String[] getAllergies() {
    return allergies;
  }

  public String getEmail() {
    return email;
  }

}
