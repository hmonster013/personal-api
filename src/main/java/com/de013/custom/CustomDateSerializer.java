package com.de013.custom;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.de013.utils.CustomFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDateSerializer extends JsonSerializer<Date>{

    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (date != null) {
            for (String format : CustomFormat.DATE) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(format);
                    gen.writeString(sdf.format(date));
                    return;
                } catch (Exception e) {
                    // Exception
                }
            }
        }
        gen.writeNull();
    }
}
