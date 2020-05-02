package com.nsd.recruitment.service;

import com.nsd.recruitment.domain.EducationExperience;

import java.util.List;

public interface EducationExperienceService {
    Long saveEducationExperience(EducationExperience educationExperience);
    List<EducationExperience> listEducationExperienceByopenid(String openid);
    int updateEducationExperience(EducationExperience educationExperience);
    EducationExperience getOneById(Long id);
    int  removeEducationExperience(Long id);
}
