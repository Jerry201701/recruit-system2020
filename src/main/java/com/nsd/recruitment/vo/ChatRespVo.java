package com.nsd.recruitment.vo;

import lombok.Data;



@Data
public class ChatRespVo {
    private Long senderId;
    /*
    1,招聘者向求职者发系统信息 2，求职者向招聘者发系统信息 3，招聘者向求职者发聊天信息 4，求职者向招聘者发聊天信息
     */
    private Integer messageType;
    private Boolean unread;
    private Long jobId;
    private String messageContent;
}
