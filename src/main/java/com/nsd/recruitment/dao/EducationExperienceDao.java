package com.nsd.recruitment.dao;

import com.nsd.recruitment.domain.EducationExperience;
import com.nsd.recruitment.tkmapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface EducationExperienceDao extends MyMapper<EducationExperience> {
}
