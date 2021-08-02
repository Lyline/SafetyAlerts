package fr.lyline.SafetyAlerts.model;

import lombok.Data;

import java.util.Date;

@Data
public class MedicalRecord {
  private String firstName;
  private String lastName;
  private Date birthdate;
  private String[] medications;
  private String[] allergies;

}
