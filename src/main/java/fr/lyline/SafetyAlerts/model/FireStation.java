package fr.lyline.SafetyAlerts.model;

/**
 The type Fire station. It forms with a station number and an address.

 @author Quesne GC
 @since 0.1 */
public class FireStation {
  private Integer station;
  private String address;

  /**
   Instantiates a new Fire station.
   */
  public FireStation() {
  }

  /**
   Instantiates a new Fire station.

   @param station the station number
   @param address the address
   */
  public FireStation(Integer station, String address) {
    this.station = station;
    this.address = address;
  }

  /**
   Gets station number.

   @return the station number
   */
  public Integer getStation() {
    return station;
  }

  /**
   Sets station number.

   @param station the station number
   */
  public void setStation(Integer station) {
    this.station = station;
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

  @Override
  public String toString() {
    return "FireStation{" +
        "station=" + station +
        ", address='" + address + '\'' +
        '}';
  }
}
