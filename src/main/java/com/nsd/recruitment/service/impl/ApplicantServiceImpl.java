package com.nsd.recruitment.service.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.result.UpdateResult;
import com.nsd.recruitment.dao.ApplicantDao;
import com.nsd.recruitment.dao.ChatRecordDao;
import com.nsd.recruitment.dao.SystemMessageDao;
import com.nsd.recruitment.dao.UserInfoDao;
import com.nsd.recruitment.domain.Applicant;
import com.nsd.recruitment.domain.ChatMessage;
import com.nsd.recruitment.domain.ChatRecord;
import com.nsd.recruitment.dto.model.DeliveryInfo;
import com.nsd.recruitment.service.ApplicantService;
import com.nsd.recruitment.vo.ApplicantRespVo;
import com.nsd.recruitment.vo.ChatRespVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Service
public class ApplicantServiceImpl implements ApplicantService {
    Logger logger= LoggerFactory.getLogger(ApplicantServiceImpl.class);
    @Autowired
    private ApplicantDao applicantDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private SystemMessageDao systemMessageDao;
    @Autowired
    private ChatRecordDao chatRecordDao;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public Long saveApplicant(Applicant applicant) {

        Timestamp present = Timestamp.valueOf(LocalDateTime.now());
        applicant.setCreateTime(present);
        int i=applicantDao.insert(applicant);
        if (i==1){
            amqpTemplate.convertAndSend("applicant", JSON.toJSONString(applicant));

        }
        return applicant.getId();
    }


    @Override
    public Applicant getDetailById(Long id) {
        return applicantDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateApplicantInfo(Applicant applicant) {
        Timestamp present = Timestamp.valueOf(LocalDateTime.now());
        applicant.setUpdateTime(present);
        int i=applicantDao.updateByPrimaryKeySelective(applicant);
        if (i==1){
            amqpTemplate.convertAndSend("applicant", JSON.toJSONString(applicant));
            return 1;
        }

        return 0;
    }

    @Override
    public List<DeliveryInfo> showAllDeliveryInfo(Long companyId) {
        List<DeliveryInfo> list=applicantDao.showAllDeliveryInfo(companyId);
        for (DeliveryInfo deliveryInfo:list){
            deliveryInfo.setEducationName(applicantDao.findEducationLevel(deliveryInfo.getApplicantId()));
        }
        return list;
    }

    @Override
    public ApplicantRespVo basicApplicantInfo(String openid) {
        return userInfoDao.showApplicantBasicByOpenid(openid);
    }

    @Override
    public Applicant applicantDetailById(Long id) {
        return applicantDao.findDetailById(id);
    }

    @Override
    public Applicant deliveryApplicantPosition(Long applicantId) {
        return applicantDao.deliveryApplicantPosition(applicantId);
    }

    @Override
    public List<ChatMessage> listMessageByReceiver(Long receiverId) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        List<ChatMessage> list=mongoTemplate.find(Query.query(Criteria.where("applicantId").is(receiverId).and("messageType").is(1)),ChatMessage.class);
//        Aggregation aggregation = Aggregation.newAggregation(
//                Aggregation.project("companyId","unreadNumber"),
//                Aggregation.match(Criteria.where("messageType").is(3).and("applicantId").is(receiverId)),
//                Aggregation.group("companyId").count().as("unreadNumber")
//                        .first("companyId").as("companyId"));
//        AggregationResults<ChatMessage> aggRes = mongoTemplate.aggregate(aggregation,
//                "chat_message", ChatMessage.class);
        Aggregation aggregation=Aggregation.newAggregation(
                Aggregation.match(Criteria.where("messageType").is(3).and("applicantId").is(receiverId)),
                Aggregation.group("companyId","jobId").count().as("unreadNumber")
                        .first("companyId").as("companyId"));
        AggregationResults<ChatMessage> aggRes = mongoTemplate.aggregate(aggregation,
                "chat_message", ChatMessage.class);



        List<ChatMessage> listRes = aggRes.getMappedResults();
        Iterator<ChatMessage> iterator=listRes.iterator();
        while (iterator.hasNext()){
            ChatMessage chatMessage=mongoTemplate.findOne(Query.query(Criteria.where("applicantId").is(receiverId)
            .and("companyId").is(iterator.next().getCompanyId())
            .and("messageType").is(3)).with(sort),ChatMessage.class);
            list.add(chatMessage);
        }
        Collections.sort(list, new Comparator<ChatMessage>() {
            @Override
            public int compare(ChatMessage o1, ChatMessage o2) {
                return  o2.getUnread().compareTo(o1.getUnread());
//                if (o1.getUnread().compareTo(o2.getUnread())==0){
//                    return  o2.getUnread().compareTo(o1.getUnread());
//                }else {
//                return o2.getCreateTime().compareTo(o1.getCreateTime());
//                }
            }
        });


        return list;
    }

    @Override
    public Integer countUnreadMessage(String openid) {
        Long applicant=applicantDao.findApplicantIdByOpenid(openid);
        Long messageNumber=mongoTemplate.count(Query.query(Criteria.where("applicantId").is(applicant)
                .and("messageType").is(1).and("unread").is(true)),ChatMessage.class);

        Integer total=messageNumber.intValue();
        Aggregation aggregation=Aggregation.newAggregation(
                Aggregation.match(Criteria.where("messageType").is(3).and("applicantId").is(applicant).and("unread").is(true)),
                Aggregation.group("companyId","jobId").sum("remind").as("unreadNumber")
                        .first("companyId").as("companyId"));
        AggregationResults<ChatMessage> aggRes = mongoTemplate.aggregate(aggregation,
                "chat_message", ChatMessage.class);
        List<ChatMessage> listRes = aggRes.getMappedResults();
        total+=listRes.size();



//        GroupByResults<ChatMessage> results = mongoTemplate.group(Criteria.where("applicantId").is(applicant).and("messageType").is(3)
//                        .and("unread").is(true),"chat_message",
//                GroupBy.key("companyId").initialDocument("{ unreadNumber: 0 }").reduceFunction("function(doc, prev) { prev.unreadNumber += 1 }"),
//                ChatMessage.class);
//        Iterator<ChatMessage> iterator=results.iterator();
//        while (iterator.hasNext()){
//            total++;
//            iterator.next();
//        }

        return total;
    }

    @Override
    public int changeMessageStatus(Long messageId) {
        Example example1 = new Example(ChatRecord.class);
        example1.createCriteria().andEqualTo("messageId",messageId);
        List<ChatRecord> list=chatRecordDao.selectByExample(example1);

        for (ChatRecord chatRecord:list){
           chatRecord.setRecordStatus(true);

        chatRecordDao.updateByPrimaryKeySelective(chatRecord);
        }

        return systemMessageDao.changeMessageStatus(messageId,false);
    }

    @Override
    public List<ChatRespVo> showChatRecordByReceiver(Long receiverId) {

        return systemMessageDao.showChatRecordByReceiver(receiverId);
    }

    @Override
    public List<ChatMessage> listChatMessage(Long messageId) {
        Query query = new Query(Criteria
                .where("messageId").is(messageId)
                .and("unread").is(true)
                .and("messageType").is(3));

        List<ChatMessage>list=mongoTemplate.find (query,ChatMessage.class);
        Update update = new Update();
        update.set("unread", false);
        UpdateResult result = mongoTemplate.updateMulti(query, update, ChatMessage.class);
        if(result.getModifiedCount()>0){
            System.out.println("修改成功");
            logger.info("更新聊天记录状态成功");
        }

        return list;
    }

    @Override
    public List<ChatMessage> chatRecordHistory(ChatMessage chatMessage) {
        Query query = new Query(Criteria
                .where("companyId").is(chatMessage.getCompanyId())
                .and("applicantId").is(chatMessage.getApplicantId())
                .and("jobId").is(chatMessage.getJobId()));
        List<ChatMessage>list=mongoTemplate.find (query,ChatMessage.class);
        return list;
    }

    @Override
    public Long findApplicantIdByOpenid(String openid) {
        return applicantDao.findApplicantIdByOpenid(openid);
    }

    @Override
    public List<Applicant> listApplicant() {
        return applicantDao.selectAll();
    }
}
