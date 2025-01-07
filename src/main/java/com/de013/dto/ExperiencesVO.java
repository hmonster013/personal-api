package com.de013.dto;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExperiencesVO  implements Serializable{
    private Long id;

    private String companyImg;
    private String companyName;
    private String jobTitle;
    private String description;
    private String workingPeriod;
    
    private Date startDate;
    private Date endDate;

    public ExperiencesVO() {
        
    }
}
