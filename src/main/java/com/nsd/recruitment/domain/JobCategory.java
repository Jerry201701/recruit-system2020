package com.nsd.recruitment.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Table(name = "job_category")
public class JobCategory {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;
    private String categoryName;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Long parentId;
    private Boolean delFlag;
}
