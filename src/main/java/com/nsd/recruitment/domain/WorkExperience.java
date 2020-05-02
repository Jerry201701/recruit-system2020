package com.nsd.recruitment.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
public class WorkExperience {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String jobName;
    private Timestamp beginTime;
    private Timestamp endTime;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Long applicantId;
    /*
    工作内容文字描述
     */
    private String responsibility;
}
