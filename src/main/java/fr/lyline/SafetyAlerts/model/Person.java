package fr.lyline.SafetyAlerts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Person {
  private String firstName;
  private String lastName;
  private String address;
  private String city;
  private int zip;
  private String phone;
  private String email;

  public Person() {
  }
}
