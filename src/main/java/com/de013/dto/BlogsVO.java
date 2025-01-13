package com.de013.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.de013.model.Skills;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogsVO implements Serializable{
    private Long id;
    private String title;
    private String imgUrl;
    private String content;
    private String description;
    private String status;
    private Date createDate;
    private Date updateDate;
    
    private List<SkillsVO> skillsVOs;
}
