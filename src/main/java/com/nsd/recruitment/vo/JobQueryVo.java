package com.nsd.recruitment.vo;

import lombok.Data;

@Data
public class JobQueryVo {
    private String regionName;
    private String categoryName;
    private Integer minSalary;
    private Integer maxSalary;
}
