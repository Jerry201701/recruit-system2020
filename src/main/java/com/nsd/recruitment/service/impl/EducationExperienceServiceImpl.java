package com.nsd.recruitment.service.impl;

import com.alibaba.fastjson.JSON;
import com.nsd.recruitment.dao.ApplicantDao;
import com.nsd.recruitment.dao.EducationExperienceDao;
import com.nsd.recruitment.domain.EducationExperience;
import com.nsd.recruitment.service.EducationExperienceService;
import com.nsd.recruitment.vo.EducationEsVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EducationExperienceServiceImpl implements EducationExperienceService {
    @Autowired
    private EducationExperienceDao educationExperienceDao;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private ApplicantDao applicantDao;

    @Override
    public Long saveEducationExperience(EducationExperience educationExperience) {

        try {
            Timestamp present = Timestamp.valueOf(LocalDateTime.now());
            educationExperience.setCreateTime(present);
            int i=educationExperienceDao.insertSelective(educationExperience);
            if (i==1){
                List<EducationEsVo>list=applicantDao.listEducationExperience(educationExperience.getApplicantId());
                 amqpTemplate.convertAndSend("education", JSON.toJSONString(list));
            }
            Long id=educationExperience.getId();
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
            return null;
    }

    @Override
    public List<EducationExperience> listEducationExperienceByopenid(String openid) {
        Example example = new Example(EducationExperience.class);
        example.createCriteria().andEqualTo("openid",openid);
        return educationExperienceDao.selectByExample(example);
    }

    @Override
    public int updateEducationExperience(EducationExperience educationExperience) {
        Timestamp present = Timestamp.valueOf(LocalDateTime.now());
        educationExperience.setUpdateTime(present);
        int i=educationExperienceDao.updateByPrimaryKeySelective(educationExperience);
        if (i==1){
            List<EducationEsVo>list=applicantDao.listEducationExperience(educationExperience.getApplicantId());
            amqpTemplate.convertAndSend("education", JSON.toJSONString(list));
        }
        return i;
    }

    @Override
    public EducationExperience getOneById(Long id) {
        return educationExperienceDao.selectByPrimaryKey(id);
    }

    @Override
    public int removeEducationExperience(Long id) {
        return educationExperienceDao.deleteByPrimaryKey(id);
    }
}
