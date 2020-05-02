package com.nsd.recruitment.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Table(name = "company_info")
public class CompanyInfo implements Serializable {

    private static final long serialVersionUID = 1361467187740021067L;
    @Id
    @GeneratedValue(generator = "JDBC")
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
    @Column(name = "company_desc")
    private String description;
    private Integer minStaff;
    private Integer maxStaff;
    /*
    1,审核中 2，审核未通过 3，审核通过 4，注销
     */
    private Integer flag;
    private String companyCode;
    private String companyLogo;




}
