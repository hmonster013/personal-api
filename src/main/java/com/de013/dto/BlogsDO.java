package com.de013.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.de013.model.Skills;

public class BlogsDO implements Serializable{
    private Long id;
    private String title;
    private String imgUrl;
    private String content;
    private String description;
    private String status;
    private Date createDate;
    private Date updateDate;
    
    private List<Skills> skills;
}
