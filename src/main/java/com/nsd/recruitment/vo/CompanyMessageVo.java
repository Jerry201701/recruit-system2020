package com.nsd.recruitment.vo;

import lombok.Data;

@Data
public class CompanyMessageVo {
    private Long jobId;
    private Long companyId;
    private String jobName;
    private String jobDescription;
    private String educationLevel;
    private String workExperience;
    private String salaryRange;
    private String certificates;
    private String address;
    private Long applicantId;

}
