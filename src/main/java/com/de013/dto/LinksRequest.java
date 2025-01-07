package com.de013.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinksRequest implements Serializable{
    private Long id;

    private String name;
    private String title;
    private String url;
    private String icon;
}
