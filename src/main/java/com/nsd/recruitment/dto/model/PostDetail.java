package com.nsd.recruitment.dto.model;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class PostDetail {
    private Long id;
    private Long companyId;
    private String jobName;
    private String educationLevel;
    private String jobDescription;
    private String salaryRange;
    private String certificates;
    private Timestamp updateTime;
    private Timestamp  createTime;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String workExperience;
    private Integer jobCategory;
    private String companyName;
    private String  brand;
    private Integer minStaff;
    private Integer maxStaff;
    private String description;
    private  Integer deliveryStatus;
    private String companyLogo;



}
