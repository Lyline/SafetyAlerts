package fr.lyline.SafetyAlerts.utils;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.joda.time.DateTime;

/**
 The type Date time module for convert the DateTime object of joda-time library for the serialization and deserialization.

 @author Quesne GC
 @see com.fasterxml.jackson.databind.module.SimpleModule
 @see org.joda.time.DateTime
 @since 0.1 */
public class DateTimeModule extends SimpleModule {

  /**
   Instantiates a new Date time module.
   */
  public DateTimeModule() {
    super();
    addSerializer(DateTime.class, new DateTimeSerializer());
    addDeserializer(DateTime.class, new DateTimeDeserializer());
  }
}
