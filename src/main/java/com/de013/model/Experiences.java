package com.de013.model;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "experiences")
public class Experiences implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyImg;
    private String companyName;
    private String jobTitle;
    private String description;
    private String workingPeriod;
    private Date startDate;
    private Date endDate;
}
