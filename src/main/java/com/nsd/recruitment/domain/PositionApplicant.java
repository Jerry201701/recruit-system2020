package com.nsd.recruitment.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "position_applicant")
@Data
public class PositionApplicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private Long applicantId;
    private Long jobId;
    private Timestamp createTime;
    private Long companyId;
    /*
    1，已投递 2,不合适 3，邀请面试  5，已面试等待结果， 4，已录用
     */
    private Integer interviewResult;

}
