package com.de013.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.de013.model.Skills;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectsVO implements Serializable {
    private Long id;

    private String name;
    private String img;
    private String description;
    private String linkGithub;
    private String linkWebsite;

    private Date startDate;
    private Date endDate;
    private List<SkillsVO> skillsVOs;
}
