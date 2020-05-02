package com.nsd.recruitment.service;

import com.nsd.recruitment.domain.WorkExperience;

import java.util.List;

public interface WorkExperienceService {
    Long saveWorkExperience(WorkExperience workExperience);
    List<WorkExperience> listWorkExperienceByopenid(String openid);
    int updateWorkExperience(WorkExperience workExperience);
    WorkExperience getOneById(Long id);
    int moveWorkExperience(Long id);

}
