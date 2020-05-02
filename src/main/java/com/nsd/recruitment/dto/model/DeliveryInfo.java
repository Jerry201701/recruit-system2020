package com.nsd.recruitment.dto.model;

import com.nsd.recruitment.domain.EducationExperience;
import com.nsd.recruitment.domain.WorkExperience;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class DeliveryInfo {
    /*
    投递记录id
     */
    private  Long id;
    /*
    简历id
     */
    private Long applicantId;
    /*
    投递公司的id
     */
    private Long companyId;
    /*
    发布岗位的id
     */
    private Long jobId;
    private String applicantName;
    /*
    求职者的openid
     */
    private String openid;
    private String telephone;
    private Integer age;
    private Integer workYears;
    private String email;
    private String introduce;
    private Timestamp deliveryTime;
    private Integer educationLevel;
    private String expectPosition;
    private String certificateList;
    private String educationName;
    private String headUrl;


}
