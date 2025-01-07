package com.de013.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.de013.custom.CustomDateConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final CustomDateConverter customDateConverter;

    public WebConfig(CustomDateConverter customDateConverter) {
        this.customDateConverter = customDateConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(customDateConverter);
    }
}

