package com.de013.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillsRequest implements Serializable{
    private Long id;
    
    private String icon;
    private String name;
}
