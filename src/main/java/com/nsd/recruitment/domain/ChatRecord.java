package com.nsd.recruitment.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Table(name = "chat_record")
public class ChatRecord implements Serializable {
    private static final long serialVersionUID = 6789659152214742563L;
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;
    private Long companyId;
    private Long applicantId;
    private Timestamp createTime;
    private String chatContent;
    /*
    1,求职者向招聘者发的信息
    2，招聘者向求职者发的聊天信息
     */
    private Integer chatType;
    /*
    true:已读，false:未读
     */
    private Boolean recordStatus;
    private Long messageId;

}
