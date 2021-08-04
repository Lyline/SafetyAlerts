package fr.lyline.SafetyAlerts.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class DateTimeDeserializer extends StdDeserializer<DateTime> {

  private static DateTimeFormatter format = DateTimeFormat.forPattern("MM/dd/yyyy");

  public DateTimeDeserializer() {
    this(null);

  }

  public DateTimeDeserializer(Class<DateTime> t) {
    super(t);
  }

  @Override
  public DateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {
    String date = jsonParser.getText();
    return format.parseDateTime(date);
  }
}
