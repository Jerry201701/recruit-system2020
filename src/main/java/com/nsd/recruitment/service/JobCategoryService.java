package com.nsd.recruitment.service;

import com.nsd.recruitment.common.PageRequest;
import com.nsd.recruitment.common.PageResult;
import com.nsd.recruitment.domain.JobCategory;
import com.nsd.recruitment.domain.RecruitRegion;

import java.util.List;

public interface JobCategoryService {
    int addJobCategory(JobCategory jobCategory);
    PageResult findJobCategoryPage(PageRequest pageRequest);
    int deleteBatch(List<JobCategory> list);
    List<JobCategory> findCategoryTree(Long parentId);
    int addRecruitRegion(RecruitRegion recruitRegion);
    PageResult findRecruitRegionPage(PageRequest pageRequest);
    int editRegion(RecruitRegion recruitRegion);
    int regionMove(List<RecruitRegion> list);
    List<RecruitRegion> findAllRegion();
}
