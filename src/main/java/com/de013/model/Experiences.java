package com.de013.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.de013.dto.ExperiencesRequest;
import com.de013.dto.ExperiencesVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "experiences")
@Getter
@Setter
public class Experiences implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyImg;
    private String companyName;
    private String jobTitle;
    private String description;
    private String workingPeriod;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    public Experiences() {

    }

    public Experiences(ExperiencesRequest request) {
        BeanUtils.copyProperties(request, this);
    }

    @JsonIgnore
    public ExperiencesVO getVO() {
        ExperiencesVO experiencesVO = new ExperiencesVO();
        BeanUtils.copyProperties(this, experiencesVO);
        return experiencesVO;
    }
}
