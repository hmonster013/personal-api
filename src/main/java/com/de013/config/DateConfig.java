package com.de013.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

import com.de013.utils.RegexFormat;

@Configuration
public class DateConfig {

    @Bean
    public Formatter<Date> dateFormatter() {
        return new Formatter<Date>() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            private final SimpleDateFormat dateOnlyFormat = new SimpleDateFormat("dd-MM-yyyy");

            @Override
            public Date parse(String text, Locale locale) throws ParseException {
                try {
                    return dateFormat.parse(text);
                } catch (ParseException e) {
                    return dateOnlyFormat.parse(text);
                }
            }

            @Override
            public String print(Date object, Locale locale) {
                return dateFormat.format(object);
            }
        };
    }

    public boolean isChecked(String dateString) {
        String regexString = RegexFormat.DATE;
        if (Pattern.matches(regexString, dateString)) {
            return true;
        }
        
        return false;
    }
}

