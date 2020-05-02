package com.nsd.recruitment.service;

import com.nsd.recruitment.domain.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    String saveChatRecord(ChatMessage chatMessage);
    List<ChatMessage> findChatRecord();
    ChatMessage findSystemRecord(ChatMessage chatMessage);
    ChatMessage updateSystemRecord(ChatMessage chatMessage);
    List<ChatMessage> findGroup();
    List<ChatMessage> listChatUnReadRecord(ChatMessage chatMessage);
    int remindAdmin(ChatMessage chatMessage);
}
