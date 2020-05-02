package com.nsd.recruitment.vo;

import lombok.Data;

@Data
public class ApplicantRespVo {
    private Long userId;
    private Long applicantId;
    //1,没有注册 2，注册没有简历 3，注册且有简历
    private Integer status;

}
