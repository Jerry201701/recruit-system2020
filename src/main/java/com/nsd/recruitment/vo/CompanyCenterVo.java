package com.nsd.recruitment.vo;

import lombok.Data;



@Data
public class CompanyCenterVo {
    private Long companyId;
    /*
    发布岗位数量
     */
    private Integer deliveryJobNumber;
    /*
    面试数量
     */
    private Integer interviewNumber;
    /*
    成功入职数量
     */
    private Integer entryNumber;
    /*
    logo图片地址
     */
    private String logoUrl;
    /*
    管理员数量
     */
    private  Integer managerNumber;

//    public CompanyCenterVo(Integer interviewNumber, Integer entryNumber) {
//        this.interviewNumber = interviewNumber;
//        this.entryNumber = entryNumber;
//    }
}
