package com.nsd.recruitment.vo;

import lombok.Data;

@Data
public class CompanyBasicRespVo {
    private Long companyId;
    private Long managerId;
    private Long userId;
    private Integer status;
}
