package com.de013.custom;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.de013.utils.CustomFormat;

import io.micrometer.common.lang.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CustomDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }

        for (String format : CustomFormat.DATE) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.parse(source);
            } catch (Exception e) {
                // Exception
            }
        }
        return null;
    }
}
