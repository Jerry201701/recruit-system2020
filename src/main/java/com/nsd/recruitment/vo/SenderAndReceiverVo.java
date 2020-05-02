package com.nsd.recruitment.vo;

import lombok.Data;

@Data
public class SenderAndReceiverVo {
    private Long applicantId;
    private String companyName;
    private String applicantName;
    private String headUrl;
    private String companyLogo;
    private Long companyId;
    private Long jobId;
    private String jobName;
    private String educationLevel;
    private String jobDescription;
    private String salaryRange;
    private String certificates;
    private String address;
    private String workExperience;
    private Integer jobCategory;
    private Integer minSalary;
    private Integer maxSalary;
    private Integer minWorkYears;
    private Integer maxWorkYears;


}
