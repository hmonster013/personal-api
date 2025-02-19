package com.de013.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins("http://localhost:4201", "http://localhost:4200")
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedHeaders("Authorization", "Content-Type", "Accept")
        .allowedHeaders("*")
        .maxAge(3600);
    }   
}

