package com.de013.model;

import java.io.Serializable;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.de013.dto.SkillsRequest;
import com.de013.dto.SkillsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "skills")
@Getter
@Setter
public class Skills implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String icon;
    private String name;

    @ManyToMany(mappedBy = "skills")
    private Set<Projects> projects;

    public Skills() {

    }
    
    public Skills(SkillsRequest skillsRequest) {
        BeanUtils.copyProperties(skillsRequest, this);
    }

    @JsonIgnore
    public SkillsVO getVO() {
        SkillsVO skillsVO = new SkillsVO();
        BeanUtils.copyProperties(this, skillsVO);
        return skillsVO;
    }
}
