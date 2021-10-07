package fr.lyline.SafetyAlerts.ObjectMapper;

import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.model.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.joda.time.DateTime;
import org.joda.time.Years;

import java.util.Arrays;

@Getter
@AllArgsConstructor
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

  public PersonInfo add(Person person, MedicalRecord medic) {
    int age = Years.yearsBetween(medic.getBirthdate(), DateTime.now()).getYears();

    return new PersonInfo(person.getFirstName(), person.getLastName(), person.getPhone(), person.getEmail(), age,
        medic.getMedications(), medic.getAllergies());
  }

  @Override
  public String toString() {
    return "PersonInfo{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", phone='" + phone + '\'' +
        ", email='" + email + '\'' +
        ", age=" + age +
        ", medications=" + Arrays.toString(medications) +
        ", allergies=" + Arrays.toString(allergies) +
        '}';
  }
}
