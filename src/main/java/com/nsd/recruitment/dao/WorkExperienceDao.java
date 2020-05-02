package com.nsd.recruitment.dao;

import com.nsd.recruitment.domain.WorkExperience;
import com.nsd.recruitment.tkmapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface WorkExperienceDao  extends MyMapper<WorkExperience> {
}
