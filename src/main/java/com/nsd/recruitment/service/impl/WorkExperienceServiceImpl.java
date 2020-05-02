package com.nsd.recruitment.service.impl;

import com.alibaba.fastjson.JSON;
import com.nsd.recruitment.dao.ApplicantDao;
import com.nsd.recruitment.dao.WorkExperienceDao;
import com.nsd.recruitment.domain.WorkExperience;
import com.nsd.recruitment.service.WorkExperienceService;
import com.nsd.recruitment.vo.WorkEsVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkExperienceServiceImpl implements WorkExperienceService {
    @Autowired
    private WorkExperienceDao workExperienceDao;
    @Autowired
    private ApplicantDao applicantDao;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public Long saveWorkExperience(WorkExperience workExperience) {
        try {
            Timestamp present = Timestamp.valueOf(LocalDateTime.now());
            workExperience.setCreateTime(present);
            int i=workExperienceDao.insertSelective(workExperience);
            if (i==1){
                List<WorkEsVo>list=applicantDao.listWorkExperience(workExperience.getApplicantId());
                amqpTemplate.convertAndSend("work", JSON.toJSONString(list));
            }
            return workExperience.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
            return null;
    }

    @Override
    public List<WorkExperience> listWorkExperienceByopenid(String openid) {
        Example example = new Example(WorkExperience.class);
        example.createCriteria().andEqualTo("openid",openid);
        List<WorkExperience>list=workExperienceDao.selectByExample(example);
        return list;
    }

    @Override
    public int updateWorkExperience(WorkExperience workExperience) {
        Timestamp present = Timestamp.valueOf(LocalDateTime.now());
        workExperience.setUpdateTime(present);
        int i=workExperienceDao.updateByPrimaryKeySelective(workExperience);
        if (i==1){
            List<WorkEsVo>list=applicantDao.listWorkExperience(workExperience.getApplicantId());
            amqpTemplate.convertAndSend("work", JSON.toJSONString(list));
        }
        return i;
    }

    @Override
    public WorkExperience getOneById(Long id) {
        return workExperienceDao.selectByPrimaryKey(id);
    }

    @Override
    public int moveWorkExperience(Long id) {
        return workExperienceDao.deleteByPrimaryKey(id);
    }
}
