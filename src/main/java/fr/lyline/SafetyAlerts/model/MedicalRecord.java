package fr.lyline.SafetyAlerts.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.lyline.SafetyAlerts.utils.DateTimeDeserializer;
import fr.lyline.SafetyAlerts.utils.DateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

@Getter
@Setter
@AllArgsConstructor
public class MedicalRecord {
  private String firstName;
  private String lastName;
  @JsonSerialize(using = DateTimeSerializer.class)
  @JsonDeserialize(using = DateTimeDeserializer.class)
  private DateTime birthdate;
  private String[] medications;
  private String[] allergies;

  public MedicalRecord() {
  }
}
