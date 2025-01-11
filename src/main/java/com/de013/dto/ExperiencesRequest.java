package com.de013.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.de013.custom.CustomDateDeserializer;
import com.de013.custom.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExperiencesRequest implements Serializable{
    private Long id;

    private String companyImg;
    private String companyName;
    private String jobTitle;
    private String description;
    private String workingPeriod;
    
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date startDate;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date endDate;

    private MultipartFile file = null;
}
