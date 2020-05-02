package com.nsd.recruitment.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;
import java.util.List;

/**
 * 求职者信息
 */
@Data
//@Document(indexName = "jobSeeker",type = "docs")
public class JobSeeker {
    @Id
    private Long id;
    private Long userId;
    private String applicantName;
    private String telephone;
    private Integer age;
    private Integer workYears;
    private String email;
    private String introduce;
    private Timestamp createTime;
    private Timestamp  updateTime;
    private Boolean flag;
    private String expectPosition;
    private String certificateList;
    private String educationLevel;
    private List<WorkExperience> workExperienceList;
    private List<EducationExperience> educationExperienceList;



}
