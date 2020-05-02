package com.nsd.recruitment.service.impl;

import com.nsd.recruitment.dao.ChatRecordDao;
import com.nsd.recruitment.domain.ChatRecord;
import com.nsd.recruitment.service.ChatRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
@Service
public class ChatRecordServiceImpl implements ChatRecordService {
    @Autowired
    private ChatRecordDao chatRecordDao;
    @Override
    public int addChatRecord(ChatRecord chatRecord) {
        chatRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return chatRecordDao.insertSelective(chatRecord);
    }

    @Override
    public List<ChatRecord> listChatRecord() {
        return null;
    }
}
