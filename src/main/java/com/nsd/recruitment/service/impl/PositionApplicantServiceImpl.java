package com.nsd.recruitment.service.impl;

import com.nsd.recruitment.dao.PositionApplicantDao;
import com.nsd.recruitment.dao.SystemMessageDao;
import com.nsd.recruitment.domain.ChatMessage;
import com.nsd.recruitment.domain.PositionApplicant;
import com.nsd.recruitment.service.ChatMessageService;
import com.nsd.recruitment.service.PositionApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class PositionApplicantServiceImpl implements PositionApplicantService {
    @Autowired
    private PositionApplicantDao positionApplicantDao;
    @Autowired
    private SystemMessageDao systemMessageDao;
    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Long savePositionApplicant(PositionApplicant positionApplicant) {
        Timestamp present = Timestamp.valueOf(LocalDateTime.now());
        ChatMessage systemMessage=new ChatMessage();
        systemMessage.setJobId(positionApplicant.getJobId());
        systemMessage.setUnread(true);
        systemMessage.setCreateTime(present);
        systemMessage.setMessageType(2);
        systemMessage.setFlag(true);
        systemMessage.setMessageContent("投递简历");
        systemMessage.setCompanyId(positionApplicant.getCompanyId());
        systemMessage.setApplicantId(positionApplicant.getApplicantId());
        mongoTemplate.insert(systemMessage);
      //  systemMessageDao.addSystemMessage(systemMessage);
        positionApplicant.setCreateTime(present);
        positionApplicantDao.insertUseGeneratedKeys(positionApplicant);
        return positionApplicant.getId();
    }

    @Override
    public int updatePositionApplicant(PositionApplicant positionApplicant) {

        PositionApplicant delivery=positionApplicantDao.selectByPrimaryKey(positionApplicant);

        ChatMessage systemMessage=new ChatMessage();
        systemMessage.setCreateTime(new Date());
        systemMessage.setCompanyId(delivery.getCompanyId());
        systemMessage.setApplicantId(delivery.getApplicantId());
        if(positionApplicant.getInterviewResult()==2){
        systemMessage.setMessageContent("不合适");
        }
        if(positionApplicant.getInterviewResult()==3){
            systemMessage.setMessageContent("邀请面试");
        }
        systemMessage.setMessageType(1);
        systemMessage.setUnread(true);
        systemMessage.setJobId(delivery.getJobId());
        ChatMessage record= chatMessageService.findSystemRecord(systemMessage);
        if (record==null){
            chatMessageService.saveChatRecord(systemMessage);

        }
        chatMessageService.updateSystemRecord(systemMessage);


        //
//        if ( systemMessageDao.findMessageByReceiverAndSender(systemMessage)==null){
////        return systemMessageDao.addSystemMessage(systemMessage);
////        }
        return positionApplicantDao.updateByPrimaryKeySelective(positionApplicant);

    }

    @Transactional
    @Override
    public int handleBatchResume(List<PositionApplicant> positionApplicantList) {
        int count=0;
        for(PositionApplicant positionApplicant :positionApplicantList){
            try {
                positionApplicantDao.updateByPrimaryKeySelective(positionApplicant);
                count++;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return count;
    }

    @Override
    public PositionApplicant getOneById(Long id) {
        return positionApplicantDao.selectByPrimaryKey(id);
    }
}
