package com.nsd.recruitment.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 招聘者信息
 */
@Data
//@Document(indexName = "jobProvider",type = "docs")
public class JobProvider {
    @Id
    private Long  id;
    private String fullName;
    private String shortName;
    private String brand;
    private Integer employeesNumber;
    private String address;
    private Timestamp createTime;
    private Timestamp updateTime;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String description;
    private Integer minStaff;
    private Integer maxStaff;
    private Boolean flag;
    private String companyCode;






}
