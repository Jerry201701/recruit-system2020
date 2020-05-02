package com.nsd.recruitment.vo;

import lombok.Data;


@Data
public class EducationEsVo {
    private Long id;
    private Long applicantId;
    private String schoolName;
    private String majorName;
    private String qualification;
}
