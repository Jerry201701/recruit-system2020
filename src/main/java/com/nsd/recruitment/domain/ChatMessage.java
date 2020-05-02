package com.nsd.recruitment.domain;

import com.nsd.recruitment.annotation.AutoIncKey;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "chat_message")
@Data
public class ChatMessage {
   // @Id
   // private ObjectId id;
    @Id
    @AutoIncKey
    private String code;
    private String recordId;
    private String messageContent;
    private Long messageId;
    private Long companyId;
    private Long applicantId;
    private Boolean flag;
    /*
    1,招聘者向求职者发系统信息 2，求职者向招聘者发系统信息 3，招聘者向求职者发聊天信息 4，求职者向招聘者发聊天信息
  5，招聘者管理的信息
     */
    private Integer messageType;
    /*
    1,未读 0，已读
     */
    private Integer remind;
    private Boolean unread;
    private Date createTime;
    private Date updateTime;
    private Integer unreadNumber;
    private Long jobId;



}
