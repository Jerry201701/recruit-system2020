package com.nsd.recruitment.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mongodb.client.result.UpdateResult;
import com.nsd.recruitment.common.ColumnFilter;
import com.nsd.recruitment.common.MybatisPageHelper;
import com.nsd.recruitment.common.PageRequest;
import com.nsd.recruitment.common.PageResult;
import com.nsd.recruitment.dao.*;
import com.nsd.recruitment.domain.*;
import com.nsd.recruitment.remote.ProducerRemote;
import com.nsd.recruitment.service.CompanyInfoService;
import com.nsd.recruitment.vo.CompanyAndManagerVo;
import com.nsd.recruitment.vo.CompanyCenterVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {
    @Autowired
    private CompanyInfoDao companyInfoDao;
    @Autowired
    private SystemMessageDao systemMessageDao;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private JobInfoDao jobInfoDao;
    @Autowired
    private ManagerDao managerDao;
    @Autowired
    private ProducerRemote producerRemote;


    @Override
    public List<CompanyInfo> findAllCompanyInfo() {

        return companyInfoDao.selectAll();
    }

    @Override
    public CompanyInfo getCompanyInfoById(Long id) {
//        Example example = new Example(CompanyInfo.class);
//        example.createCriteria().andEqualTo("companyCode",code);

        return companyInfoDao.selectByPrimaryKey(id);
    }

    @Override
    public List<CompanyInfo> getCompanysByName(CompanyInfo companyInfo) {
        return null;
    }
    @Transactional
    @Override
    public Long saveCompany(CompanyInfo companyInfo) {
        //companyInfo.setCompanyCode(JobUtil.getCompanyCode());
        List<Long>names=companyInfoDao.findIdByFullName(companyInfo.getFullName());
        if (names.size()>0){
            return 0L;
        }
        Timestamp present = Timestamp.valueOf(LocalDateTime.now());
        companyInfo.setCreateTime(present);
        companyInfo.setFlag(1);
        int i=companyInfoDao.insertUseGeneratedKeys(companyInfo);
        Long id=companyInfo.getId();
        if ( i==1){
            amqpTemplate.convertAndSend("company", JSON.toJSONString(companyInfo));

        }
       return id;
    }

    @Override
    public int updateCompany(CompanyInfo companyInfo) {
        Timestamp present = Timestamp.valueOf(LocalDateTime.now());
        companyInfo.setUpdateTime(present);
        int i=companyInfoDao.updateByPrimaryKeySelective(companyInfo);
        if ( i==1){
            amqpTemplate.convertAndSend("company", JSON.toJSONString(companyInfo));
            return  1;
        }
        return 0;
    }

    @Override
    public int moveCompany(CompanyInfo companyInfo) {
        return 0;
    }

    @Override
    public List<CompanyInfo> queryOnConditions(CompanyInfo companyInfo) {
        return null;
    }

    @Override
    public Page<CompanyInfo> findByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return companyInfoDao.findByPage();
    }

    @Override
    public CompanyInfo showCompanyInfo(String code) {
        Example example = new Example(CompanyInfo.class);
        example.createCriteria().andEqualTo("companyCode",code);

        return companyInfoDao.selectOneByExample(example);
    }


    @Override
    public List<CompanyInfo> findCompanyByKeyWord(String keyWord,Integer choose) {
      return   producerRemote.searchCompanyByKeyword(keyWord,choose);

      //  return companyInfoDao.findCompanyByKeyWord(keyWord);
    }

    @Override
    public Long saveChatRecord(ChatMessage systemMessage) {

        systemMessage.setCreateTime(new Timestamp(System.currentTimeMillis()));
        Long messageId=systemMessageDao.findMessageByReceiverAndSender(systemMessage);
        if (messageId==null){
             systemMessageDao.addSystemMessage(systemMessage);

        }else {
            if(systemMessage.getUnread()){
                systemMessage.setMessageId(messageId);

            }
        }
        mongoTemplate.insert(systemMessage);
        return systemMessage.getMessageId();

    }

    @Override
    public List<ChatMessage> showChatHistory(ChatMessage chatMessage) {
       // Long messageId=systemMessageDao.findMessageByReceiverAndSender(systemMessage);
        Query query = new Query(Criteria
                .where("companyId").is(chatMessage.getCompanyId())
                .and("applicantId").is(chatMessage.getApplicantId())
                .and("jobId").is(chatMessage.getJobId()));
       return mongoTemplate.find (query,ChatMessage.class);


    }

    @Override
    public List<ChatMessage> listMessageByCompany(Long companyId) {
       // Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        Query query=new Query();
        query.addCriteria(Criteria.where("companyId").is(companyId));
        query.addCriteria(Criteria.where("messageType").in(2,5));
        List<ChatMessage> list=mongoTemplate.find(query,ChatMessage.class);
      //  List<ChatMessage> list=mongoTemplate.find(Query.query(Criteria.where("companyId").is(companyId)),ChatMessage.class);

//        Aggregation aggregation = Aggregation.newAggregation(
//                Aggregation.project("companyId","unreadNumber"),
//                Aggregation.match(Criteria.where("messageType").is(2).and("applicantId").is(receiverId)),
//                Aggregation.group("companyId").count().as("unreadNumber")
//                        .first("companyId").as("companyId"));

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("messageType").is(4).and("companyId").is(companyId)),
                Aggregation.group("applicantId","jobId").sum("remind").as("unreadNumber")
                        .last("messageContent").as("messageContent")
                        .last(("unread")).as("unread"));
//                        Aggregation.group("applicantId","jobId").sum().as("unreadNumber")
//                        .last("messageContent").as("messageContent")
//                        .last(("unread")).as("unread"));
        AggregationResults<ChatMessage> aggRes = mongoTemplate.aggregate(aggregation, "chat_message", ChatMessage.class);
        List<ChatMessage> listRes = aggRes.getMappedResults();
        Iterator<ChatMessage> iterator=listRes.iterator();
        while (iterator.hasNext()){
//            ChatMessage chatMessage=mongoTemplate.findOne(Query.query(Criteria.where("companyId").is(companyId)
//                    .and("applicantId").is(iterator.next().getCompanyId())
//                    .and("messageType").is(4)).with(sort),ChatMessage.class);
            list.add(iterator.next());
        }

//        Collections.sort(list, new Comparator<ChatMessage>() {
//            @Override
//            public int compare(ChatMessage o1, ChatMessage o2) {
//                return  o2.getUnread().compareTo(o1.getUnread());
//                if (o1.getUnread().compareTo(o2.getUnread())==0){
//                    return  o2.getUnread().compareTo(o1.getUnread());
//                }else {
//                return o2.getCreateTime().compareTo(o1.getCreateTime());
//                }
//            }
//        });

        return list;
    }

    @Override
    public List<ChatMessage> unreadChatRecord(ChatMessage chatMessage) {
        Query query = new Query(Criteria
                .where("companyId").is(chatMessage.getCompanyId())
                .and("applicantId").is(chatMessage.getApplicantId())
                .and("jobId").is(chatMessage.getJobId())
                .and("unread").is(true)
                .and("messageType").is(4));

        List<ChatMessage>list=mongoTemplate.find (query,ChatMessage.class);
        Update update = new Update();
        update.set("unread", false);
        update.set("remind",0);
        UpdateResult result = mongoTemplate.updateMulti(query, update, ChatMessage.class);
        if(result.getModifiedCount()>0){
            System.out.println("修改成功");
        }

        return list;
    }

    @Override
    public List<Applicant> findApplicantByKeyword(String keyword) {
        return null;
    }


    @Override
    public CompanyCenterVo companyCenter(Long companyId) {
        //mybatis返回默认类型为bigdecimal
        CompanyCenterVo company=new CompanyCenterVo();

        String logo=companyInfoDao.selectByPrimaryKey(companyId).getCompanyLogo();
        company.setCompanyId(companyId);
        company.setLogoUrl(logo);
        CompanyCenterVo companyCenterVo=companyInfoDao.findCenterNumber(companyId);
        if (companyCenterVo==null){
        company.setInterviewNumber(0);
        company.setEntryNumber(0);
        }else {
            company.setEntryNumber(companyCenterVo.getEntryNumber());
            company.setInterviewNumber(companyCenterVo.getInterviewNumber());
        }
        Example example = new Example(JobInfo.class);
        example.createCriteria().andEqualTo("companyId",companyId);
        Integer jobNumber=jobInfoDao.selectCountByExample(example);
        company.setDeliveryJobNumber(jobNumber);
        Integer managerNumber=managerDao.countManagerNumber(companyId);
        company.setManagerNumber(managerNumber);

        return company;
    }

    @Override
    public CompanyAndManagerVo companyAndManager(Long companyId) {
        CompanyAndManagerVo companyAndManagerVo=companyInfoDao.companyDetailAndManager(companyId);
        ManagerInfo managerInfo=managerDao.findRecentManagerByCompany(companyId);
        if (managerInfo!=null){
            companyAndManagerVo.setManagerId(managerInfo.getId());
            companyAndManagerVo.setManagerName(managerInfo.getName());
        }
        return companyAndManagerVo;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        String status = getColumnFilterValue(pageRequest, "label");
        if (status==null){
        return MybatisPageHelper.findPage(pageRequest,companyInfoDao,"findPage");
        }
        return MybatisPageHelper.findPage(pageRequest,companyInfoDao,"findUncheckPage");
    }

    /**
     * 获取过滤字段的值
     * @param filterName
     * @return
     */
    public String getColumnFilterValue(PageRequest pageRequest, String filterName) {
        String value = null;
        ColumnFilter columnFilter = pageRequest.getColumnFilter(filterName);
        if(columnFilter != null) {
            value = columnFilter.getValue();
        }
        return value;
    }

    @Override
    public int checkCompany(CompanyInfo companyInfo) {
       return companyInfoDao.updateByPrimaryKeySelective(companyInfo);
    }

    @Override
    public CompanyInfo detailForChat(Long companyId) {
        return companyInfoDao.selectByPrimaryKey(companyId);
    }

    @Override
    public List<Long> findIdByFullName(String fullName) {
        return companyInfoDao.findIdByFullName(fullName);
    }
}
