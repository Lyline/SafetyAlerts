package fr.lyline.SafetyAlerts.ObjectMapper;

import fr.lyline.SafetyAlerts.model.MedicalRecord;
import fr.lyline.SafetyAlerts.model.Person;
import org.joda.time.DateTime;
import org.joda.time.Years;

import java.util.Arrays;

/**
 The object mapper of Person info. Its concatenate the first name, the last name, the phone number, the email, the age, the
 medications list and allergies list, its use by the endpoint service API.

 @author Quesne GC
 @since 0.1 */
public class PersonInfo {
  /**
   The First name.
   */
  String firstName;
  /**
   The Last name.
   */
  String lastName;
  /**
   The Phone.
   */
  String phone;
  /**
   The Email.
   */
  String email;
  /**
   The Age.
   */
  int age;
  /**
   The Medications.
   */
  String[] medications;
  /**
   The Allergies.
   */
  String[] allergies;

  /**
   Instantiates a new Person info.
   */
  public PersonInfo() {
  }

  /**
   Instantiates a new Person info.

   @param firstName   the first name
   @param lastName    the last name
   @param phone       the phone
   @param email       the email
   @param age         the age
   @param medications the array of medications
   @param allergies   the array of allergies
   */
  public PersonInfo(String firstName, String lastName, String phone, String email,
                    int age, String[] medications, String[] allergies) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.email = email;
    this.age = age;
    this.medications = medications;
    this.allergies = allergies;
  }

  /**
   Instantiates a new Person info.

   @param firstName   the first name
   @param lastName    the last name
   @param phone       the phone
   @param age         the age
   @param medications the array of medications
   @param allergies   the array of allergies
   */
  public PersonInfo(String firstName, String lastName, String phone,
                    int age, String[] medications, String[] allergies) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.age = age;
    this.medications = medications;
    this.allergies = allergies;
  }

  /**
   Create a new person info from to person identification and medical record.

   @param person the person
   @param medic  the medic

   @return the person info

   @see fr.lyline.SafetyAlerts.model.Person
   @see fr.lyline.SafetyAlerts.model.MedicalRecord
   */
  public PersonInfo create(Person person, MedicalRecord medic) {
    int age = Years.yearsBetween(medic.getBirthdate(), DateTime.now()).getYears();

    return new PersonInfo(person.getFirstName(), person.getLastName(), person.getPhone(), person.getEmail(), age,
        medic.getMedications(), medic.getAllergies());
  }

  /**
   Gets first name.

   @return the first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   Gets last name.

   @return the last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   Gets phone.

   @return the phone
   */
  public String getPhone() {
    return phone;
  }

  /**
   Gets email.

   @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   Gets age.

   @return the age
   */
  public int getAge() {
    return age;
  }

  /**
   Get medications string [ ].

   @return the string [ ]
   */
  public String[] getMedications() {
    return medications;
  }

  /**
   Get allergies string [ ].

   @return the string [ ]
   */
  public String[] getAllergies() {
    return allergies;
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
