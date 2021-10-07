package fr.lyline.SafetyAlerts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FireStation {
  private Integer station;
  private String address;

  public FireStation() {
  }

  @Override
  public String toString() {
    return "FireStation{" +
        "station=" + station +
        ", address='" + address + '\'' +
        '}';
  }
}
