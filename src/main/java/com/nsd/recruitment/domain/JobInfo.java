package com.nsd.recruitment.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Table(name = "job_info")
public class JobInfo {
    //@Id
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;
    private Long companyId;
    private String jobName;
    private String educationLevel;
    private String jobDescription;
    private String salaryRange;
    private String certificates;
    private Timestamp updateTime;
    private Timestamp  createTime;
    private Integer  updateBy;
    private Integer  createBy;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer flag;
    private String workExperience;
    private Integer jobCategory;
    private Integer minSalary;
    private Integer maxSalary;
    private Integer minWorkYears;
    private Integer maxWorkYears;


}
