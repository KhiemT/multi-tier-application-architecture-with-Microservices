package com.mycompany.app.transaction.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * Created by Khiem on 10/29/2016.
 * JSON format joda datetime
 */
public class CustomDateSerializer extends JsonSerializer<DateTime> {
    private static DateTimeFormatter formatter =
            DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void serialize(DateTime value, JsonGenerator gen, SerializerProvider arg)
            throws IOException {
        gen.writeString(formatter.print(value));
    }
}
