package com.nsd.recruitment.vo;

import lombok.Data;

@Data
public class EmployeeMessageVo {
    private Long messageId;
    private String messageContent;
    private Integer messageType;
    private Long jobId;
    private Boolean unread;


}
