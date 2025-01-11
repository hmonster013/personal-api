package com.de013.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.de013.dto.ProjectsRequest;
import com.de013.dto.ProjectsVO;
import com.de013.dto.SkillsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "projects")
@Getter
@Setter
public class Projects implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String img;
    private String description;
    private String linkGithub;
    private String linkWebsite;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @ManyToMany
    @JoinTable(
        name = "projects_skills",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skills> skills;

    public Projects() {
        
    }

    public Projects(ProjectsRequest request) {
        this.id = request.getId();
        this.name = request.getName();
        this.img = request.getImg();
        this.description = request.getDescription();
        this.linkGithub = request.getLinkGithub();
        this.linkWebsite = request.getLinkWebsite();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.skills = new ArrayList<>();
    }

    @JsonIgnore
    public ProjectsVO getVO() {
        ProjectsVO projectsVO = new ProjectsVO();
        BeanUtils.copyProperties(this, projectsVO);
        projectsVO.setSkillsVOs(this.skills.stream().map(Skills::getVO).toList());
        return projectsVO;
    }
}
