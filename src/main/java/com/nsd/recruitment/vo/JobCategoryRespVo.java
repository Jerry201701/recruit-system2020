package com.nsd.recruitment.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class JobCategoryRespVo {
    private String categoryName;
    private Long id;
    private String parent;
    private Long parentId;
    private Timestamp createTime;

}
