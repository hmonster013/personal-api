package com.de013.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FilterVO implements Serializable {
    Long id;
    String name;
    int page;
    int size;
}
