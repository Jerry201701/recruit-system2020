package com.nsd.recruitment.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostJobVo {
    private Long jobId;
    private Long companyId;
    private String jobName;
    private String companyLogo;
    private String brand;
    private Timestamp createTime;
    private Integer minSalary;
    private Integer maxSalary;
    private Integer minWorkYears;
    private Integer maxWorkYears;
    private String address;
}
