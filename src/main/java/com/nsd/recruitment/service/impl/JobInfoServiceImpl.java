package com.nsd.recruitment.service.impl;

import com.alibaba.fastjson.JSON;
import com.nsd.recruitment.dao.JobInfoDao;
import com.nsd.recruitment.domain.JobInfo;
import com.nsd.recruitment.dto.model.PostDetail;
import com.nsd.recruitment.remote.ProducerRemote;
import com.nsd.recruitment.service.JobInfoService;
import com.nsd.recruitment.vo.CompanyMessageVo;
import com.nsd.recruitment.vo.JobQueryVo;
import com.nsd.recruitment.vo.PostJobVo;
import com.nsd.recruitment.vo.SenderAndReceiverVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobInfoServiceImpl implements JobInfoService {
    @Autowired
    private JobInfoDao jobInfoDao;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private ProducerRemote producerRemote;


    @Override
    public int saveJobInfo(JobInfo jobInfo) {
        //DateTimeFormatter
        //Instant instant=new Instant();
       // LocalDateTime
        Timestamp present = Timestamp.valueOf(LocalDateTime.now());
        jobInfo.setCreateTime(present);
        int i=jobInfoDao.insert(jobInfo);
        if (i==1){
            amqpTemplate.convertAndSend("job", JSON.toJSONString(jobInfo));
        }
        return i;
    }

    @Override
    public List<JobInfo> showAllJobs(Long companyId) {
         Example example = new Example(JobInfo.class);
          example.createCriteria().andEqualTo("companyId",companyId);
        return jobInfoDao.selectByExample(example);
    }

    @Override
    public JobInfo getDetailById(Long id) {
       // Example example = new Example(JobInfo.class);
      //  example.createCriteria().andEqualTo("id",id);
       // return jobInfoDao.selectOneByExample(example);
        return jobInfoDao.selectByPrimaryKey(id);

    }


    @Override
    public int updateJobInfo(JobInfo jobInfo) {
        Timestamp present = Timestamp.valueOf(LocalDateTime.now());
        jobInfo.setUpdateTime(present);
        return jobInfoDao.updateByPrimaryKeySelective(jobInfo);
    }

    @Override
    public PostDetail getPostDetailByApplicant(Long id,String openid) {
        PostDetail postDetail= jobInfoDao.getPostDetailByApplicant(id,openid);
        return postDetail;
    }

    @Override
    public CompanyMessageVo showChatJobInfo(Long deliveryId) {
        return jobInfoDao.showChatJobInfo(deliveryId);
    }

    @Override
    public PostDetail showPostDetailByCompanyId(Long jobId) {
        return jobInfoDao.showPostDetailByCompanyId(jobId);
    }

    @Override
    public List<JobInfo> listJobByCompany(Long companyId) {
         Example example = new Example(JobInfo.class);
          example.createCriteria().andEqualTo("companyId",companyId);
         return jobInfoDao.selectByExample(example);
    }

    @Override
    public List<PostJobVo> findJobsByApplicant(Long applicantId) {
        return jobInfoDao.findDeliveryJobByApplicant(applicantId);
        //return jobInfoDao.selectAll();
    }

    @Override
    public List<JobInfo> listAllJobs() {



        return jobInfoDao.selectAll();
    }

    @Override
    public List<PostJobVo> listAllPostJobs() {

        return  jobInfoDao.listAllPostJobs();
    }

    @Override
    public SenderAndReceiverVo findChatJob(Long jobId) {


        return jobInfoDao.findChatJob(jobId);
    }


    @Override
    public List<JobInfo> searchJobByKeyword(JobQueryVo jobQueryVo) {
        return producerRemote.searchJobByKeyword(jobQueryVo);
    }

    @Override
    public List<PostJobVo> findJobsByConditions(JobQueryVo jobQueryVo) {

        return jobInfoDao.findJobsByConditions(jobQueryVo);
    }
}
