package com.nsd.recruitment.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ManagerInfo {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private Boolean sex;
    /*
    1,等待审核 2，审核通过 3，跟公司解除绑定 4:审核不通过
     */
    private Integer flag;
    private Timestamp createTime;
    private Timestamp lastUpdateTime;
    private String remark;
    private Long companyId;
    private Long userId;
}
