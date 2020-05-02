package com.nsd.recruitment.service;

import com.nsd.recruitment.domain.ChatRecord;

import java.util.List;

public interface ChatRecordService {
    int addChatRecord(ChatRecord chatRecord);
    List<ChatRecord> listChatRecord();

}
