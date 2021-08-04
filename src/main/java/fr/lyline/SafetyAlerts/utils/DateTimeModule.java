package fr.lyline.SafetyAlerts.utils;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.joda.time.DateTime;

public class DateTimeModule extends SimpleModule {

  public DateTimeModule() {
    super();
    addSerializer(DateTime.class, new DateTimeSerializer());
    addDeserializer(DateTime.class, new DateTimeDeserializer());
  }
}
