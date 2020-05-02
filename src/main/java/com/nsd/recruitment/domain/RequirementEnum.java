package com.nsd.recruitment.domain;

public enum RequirementEnum {
    Health_CERTIFICATE("健康证",1),
    CHINESE_FOOD("中餐证",2),
    GRADUATE_STUDENT("应届生",3),
    ONT_THREE("1-3年",4),
    THREE_FIVE("3-5年",5),
    MORE_THAN_FIVE("5年以上",6),
    PRIMARY_SCHOOL("小学",7),
    JUNIOR_MIDDLE_SCHOOL("初中",8),
    SENIOR_SCHOOL("高中",9),
    UNIVERSITY("大学",11),
    SALARY_ONE("1k-3k",12),
    SALARY_TWO("3k-5k",13),
    SALARY_THREE("5k-10k",14),
    SALARY_FOUR("10k以上",15),
    POST_CLOSED("已关闭",16),
    POST_AWAIT("待开放",17),
    POST_OPENED("已开放",18),
    POST_UNPASSED("审核未通过",19),
    ALREADY_DELIVERY("已投递",20),
    UN_PASSED("不合适",21)



     /*
    1，已投递 2,不合适 3，邀请面试  5，已面试等待结果， 4，已录用
     */

    ;
    private RequirementEnum(String certificateName, Integer code){
        this.certificateName=certificateName;
        this.code=code;
    }
    private String certificateName;
    private Integer code;


    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
