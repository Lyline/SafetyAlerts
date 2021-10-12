package fr.lyline.SafetyAlerts.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 The Date time serializer for the library Joda-Time.

 @author Quesne GC
 @see com.fasterxml.jackson.databind.SerializerProvider
 @see org.joda.time.DateTime
 @see org.joda.time.format.DateTimeFormatter
 @since 0.1 */
public class DateTimeSerializer extends JsonSerializer<DateTime> {

  private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");

  @Override
  public void serialize(DateTime dateTime, JsonGenerator jsonGenerator,
                        SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(formatter.print(dateTime));

  }

}
