package com.nsd.recruitment.vo;

import lombok.Data;


import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class CompanyAndManagerVo {
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
    private Integer flag;
    private String companyCode;
    private String companyLogo;
    private Long managerId;
    private String managerName;

}
