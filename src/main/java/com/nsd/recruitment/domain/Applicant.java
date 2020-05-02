package com.nsd.recruitment.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
public class Applicant implements Serializable {
    private static final long serialVersionUID = -8600968162619955884L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String applicantName;
    private String telephone;
    private Integer age;
    private Integer workYears;
    private String email;
    /*
    自我描述
     */
    private String introduce;
    private Timestamp  createTime;
    private Timestamp  updateTime;
    private Boolean flag;
    private String expectPosition;
    private String certificateList;
    private String educationLevel;
    private String headUrl;
    private List<WorkExperience> workExperienceList;
    private List<EducationExperience> educationExperienceList;
    private Integer maxExpectSalary;
    private Integer minExpectSalary;


}
