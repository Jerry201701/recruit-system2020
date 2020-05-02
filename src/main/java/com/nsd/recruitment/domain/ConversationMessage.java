package com.nsd.recruitment.domain;

import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;
import java.sql.Timestamp;

@Document(collection = "conversation_message")
@Data
public class ConversationMessage {

    private String id;
    private Long receiverId;
    private Timestamp createTime;
    private String messageContent;
    private Long messageId;
    private Boolean unread;
}
