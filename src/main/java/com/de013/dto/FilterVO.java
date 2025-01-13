package com.de013.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class FilterVO implements Serializable {
    Long id;
    String name;
    String title;
    String companyName;
    int page;
    int size;
}
