package com.nsd.recruitment.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.nsd.recruitment.domain.ChatMessage;
import com.nsd.recruitment.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public String saveChatRecord(ChatMessage chatMessage) {
        //chatMessage.setRecordId(chatMessage.getId().toString());
        chatMessage.setCreateTime(new Date());
        if (chatMessage.getUnread()){
            chatMessage.setRemind(1);
        }
       ChatMessage record= mongoTemplate.insert(chatMessage);



        return record.getCode();
    }

    @Override
    public List<ChatMessage> findChatRecord() {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
      //  mongoTemplate.findo
        List<ChatMessage> list= mongoTemplate.find(Query.query(Criteria.where("")).with(sort),ChatMessage.class);
        return list;
    }

    @Override
    public ChatMessage findSystemRecord(ChatMessage chatMessage) {
        Query query = new Query(Criteria
                .where("companyId").is(chatMessage.getCompanyId())
                .and("applicantId").is(chatMessage.getApplicantId())
                .and("jobId").is(chatMessage.getJobId())
                .and("messageType").is(chatMessage.getMessageType()));
        return  mongoTemplate.findOne(query,ChatMessage.class);

//        Update update = new Update();
//        update.set("companyId", chatMessage.getCompanyId());
//        update.set("applicantId", chatMessage.getApplicantId());
//        update.set("messageType", chatMessage.getMessageType());
//        update.set("unread",true);
//        UpdateResult updateResult =mongoTemplate.upsert(query, update, ChatMessage.class);




      //  return updateResult.getModifiedCount();
    }

    @Override
    public ChatMessage updateSystemRecord(ChatMessage chatMessage) {
        Query query = new Query(Criteria
                .where("companyId").is(chatMessage.getCompanyId())
                .and("applicantId").is(chatMessage.getApplicantId())
                .and("jobId").is(chatMessage.getJobId())
                .and("messageType").is(chatMessage.getMessageType()));
                Update update = new Update();
            update.set("messageContent",chatMessage.getMessageContent());
            update.set("unread",true);
//        update.set("applicantId", chatMessage.getApplicantId());
//        update.set("messageType", chatMessage.getMessageType());
//        update.set("unread",true);
//        UpdateResult updateResult =mongoTemplate.upsert(query, update, ChatMessage.class);

        return  mongoTemplate.findAndModify(query,update,ChatMessage.class);

      //  return null;
    }

    @Override
    public List<ChatMessage> findGroup() {

//        GroupBy groupBy = new GroupBy("分组字段")
//                .initialDocument("{ count: 0 }")
//                .reduceFunction("function (doc,pre){pre.count +=1 ;}");

//        GroupByResults<ChatMessage> group = mongoTemplate.group(
//                                Criteria.where("applicantId").is(21),
//                               "chat_message",
//                               new GroupBy("messageType"),
//                ChatMessage.class);
       // GroupBy groupBy = GroupBy.key("messageContent,code,messageType");
      //  mongoTemplate.group("chat.chat_message",groupBy,ChatMessage.class);
      //  Criteria criteria=new Criteria();

        GroupByResults<ChatMessage> results = mongoTemplate.group(Criteria.where("applicantId").is(23).and("messageType").is(3)
                        .and("unread").is(true),"chat_message",
                GroupBy.key("companyId").initialDocument("{ unreadNumber: 0 }").reduceFunction("function(doc, prev) { prev.unreadNumber += 1 }"),
                ChatMessage.class);



        return null;
    }


    @Override
    public List<ChatMessage> listChatUnReadRecord(ChatMessage chatMessage) {
        Query query=new Query();
        query.addCriteria(Criteria.where("applicantId").is(chatMessage.getApplicantId())
                .and("companyId").is(chatMessage.getCompanyId()).and("messageType").is(chatMessage.getMessageType())
                .and("unread").is(true).and("jobId").is(chatMessage.getJobId()));
        List<ChatMessage>list=mongoTemplate.find(query,ChatMessage.class);
        Update update = Update.update("unread",false);
        mongoTemplate.updateMulti(query,update,ChatMessage.class);


        return list;
    }

    @Override
    public int remindAdmin(ChatMessage chatMessage) {



        return 0;
    }
}
