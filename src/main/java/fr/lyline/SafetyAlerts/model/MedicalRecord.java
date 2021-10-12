package fr.lyline.SafetyAlerts.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.lyline.SafetyAlerts.utils.DateTimeDeserializer;
import fr.lyline.SafetyAlerts.utils.DateTimeSerializer;
import org.joda.time.DateTime;

import java.util.Arrays;

/**
 The type Medical record. It forms with a first name, a last name, a birthdate, a list of medications and a list of
 allergies.

 @author Quesne GC
 @since 0.1 */
public class MedicalRecord {
  private String firstName;
  private String lastName;
  @JsonSerialize(using = DateTimeSerializer.class)
  @JsonDeserialize(using = DateTimeDeserializer.class)
  private DateTime birthdate;
  private String[] medications;
  private String[] allergies;

  /**
   Instantiates a new Medical record.
   */
  public MedicalRecord() {
  }

  /**
   Instantiates a new Medical record.

   @param firstName   the first name
   @param lastName    the last name
   @param birthdate   the birthdate
   @param medications the medications list
   @param allergies   the allergies list
   */
  public MedicalRecord(String firstName, String lastName, DateTime birthdate,
                       String[] medications, String[] allergies) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthdate = birthdate;
    this.medications = medications;
    this.allergies = allergies;
  }

  /**
   Gets first name.

   @return the first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   Sets first name.

   @param firstName the first name
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   Gets last name.

   @return the last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   Sets last name.

   @param lastName the last name
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   Gets birthdate.

   @return the birthdate

   @see org.joda.time.DateTime
   */
  public DateTime getBirthdate() {
    return birthdate;
  }

  /**
   Sets birthdate.

   @param birthdate the birthdate

   @see org.joda.time.DateTime
   */
  public void setBirthdate(DateTime birthdate) {
    this.birthdate = birthdate;
  }

  /**
   Get medications string [ ].

   @return the string medications list [ ]
   */
  public String[] getMedications() {
    return medications;
  }

  /**
   Sets medications.

   @param medications the medications
   */
  public void setMedications(String[] medications) {
    this.medications = medications;
  }

  /**
   Get allergies string [ ].

   @return the string allergies list [ ]
   */
  public String[] getAllergies() {
    return allergies;
  }

  /**
   Sets allergies.

   @param allergies the allergies
   */
  public void setAllergies(String[] allergies) {
    this.allergies = allergies;
  }

  @Override
  public String toString() {
    return "MedicalRecord{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", birthdate=" + birthdate +
        ", medications=" + Arrays.toString(medications) +
        ", allergies=" + Arrays.toString(allergies) +
        '}';
  }
}
