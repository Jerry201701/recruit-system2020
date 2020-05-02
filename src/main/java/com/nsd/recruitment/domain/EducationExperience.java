package com.nsd.recruitment.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
public class EducationExperience {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String schoolName;
    private String majorName;
    private String qualification;
    private Long applicantId;
    private Timestamp beginTime;
    private Timestamp endTime;
    private Timestamp createTime;
    private Timestamp updateTime;
    private  String educationDesc;


}
