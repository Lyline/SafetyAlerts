package fr.lyline.SafetyAlerts.model;

/**
 The type Person. It forms with a first name, a last name, an address, a city, a zip code, a phone number and an email.

 @author Quesne GC
 @since 0.1 */
public class Person {
  private String firstName;
  private String lastName;
  private String address;
  private String city;
  private int zip;
  private String phone;
  private String email;

  /**
   Instantiates a new Person.
   */
  public Person() {
  }

  /**
   Instantiates a new Person.

   @param firstName the first name
   @param lastName  the last name
   @param address   the address
   @param city      the city
   @param zip       the zip
   @param phone     the phone
   @param email     the email
   */
  public Person(String firstName, String lastName, String address, String city, int zip, String phone, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.city = city;
    this.zip = zip;
    this.phone = phone;
    this.email = email;
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
   Gets address.

   @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   Sets address.

   @param address the address
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   Gets city.

   @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   Sets city.

   @param city the city
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   Gets zip.

   @return the zip
   */
  public int getZip() {
    return zip;
  }

  /**
   Sets zip.

   @param zip the zip
   */
  public void setZip(int zip) {
    this.zip = zip;
  }

  /**
   Gets phone.

   @return the phone
   */
  public String getPhone() {
    return phone;
  }

  /**
   Sets phone.

   @param phone the phone
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   Gets email.

   @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   Sets email.

   @param email the email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "Person{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", address='" + address + '\'' +
        ", city='" + city + '\'' +
        ", zip=" + zip +
        ", phone='" + phone + '\'' +
        ", email='" + email + '\'' +
        "}";
  }
}
