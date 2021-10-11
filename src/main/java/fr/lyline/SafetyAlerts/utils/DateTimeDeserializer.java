package fr.lyline.SafetyAlerts.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 The Date time deserializer for the library Joda-Time.

 @author Quesne GC
 @see com.fasterxml.jackson
 @see org.joda.time.DateTime
 @since 0.1 */
public class DateTimeDeserializer extends StdDeserializer<DateTime> {

  private static final DateTimeFormatter format = DateTimeFormat.forPattern("MM/dd/yyyy");

  /**
   Instantiates a new Date time deserializer.
   */
  public DateTimeDeserializer() {
    this(null);
  }

  /**
   Instantiates a new Date time deserializer.

   @param date the date to deserialize

   @see org.joda.time.DateTime
   */
  public DateTimeDeserializer(Class<DateTime> date) {
    super(date);
  }

  @Override
  public DateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    String date = jsonParser.getText();
    return format.parseDateTime(date);
  }
}
