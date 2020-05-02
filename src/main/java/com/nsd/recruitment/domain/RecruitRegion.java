package com.nsd.recruitment.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Table(name = "recruit_region")
public class RecruitRegion {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;
    private String regionName;
    private String remark;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Boolean delFlag;
}
