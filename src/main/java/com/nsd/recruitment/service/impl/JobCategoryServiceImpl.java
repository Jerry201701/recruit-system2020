package com.nsd.recruitment.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.nsd.recruitment.common.MybatisPageHelper;
import com.nsd.recruitment.common.PageRequest;
import com.nsd.recruitment.common.PageResult;
import com.nsd.recruitment.dao.JobCategoryDao;
import com.nsd.recruitment.dao.ManagerDao;
import com.nsd.recruitment.dao.RecruitRegionDao;
import com.nsd.recruitment.domain.Customers;
import com.nsd.recruitment.domain.JobCategory;
import com.nsd.recruitment.domain.RecruitRegion;
import com.nsd.recruitment.service.JobCategoryService;
import com.nsd.recruitment.vo.JobCategoryRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.List;

@Service
public class JobCategoryServiceImpl implements JobCategoryService {
    @Autowired
    private JobCategoryDao jobCategoryDao;
    @Autowired
    private ManagerDao managerDao;
    @Autowired
    private RecruitRegionDao recruitRegionDao;
    @Override
    public int addJobCategory(JobCategory jobCategory) {
        if (jobCategory.getId()==null||jobCategory.getId()==0){
        return jobCategoryDao.insertUseGeneratedKeys(jobCategory);

        }
        return jobCategoryDao.updateByPrimaryKeySelective(jobCategory);
    }
    @Override
    public PageResult findJobCategoryPage(PageRequest pageRequest) {
       // PageHelper.startPage(pageRequest.getPageNum(),pageRequest.getPageSize());
        return MybatisPageHelper.findPage(pageRequest,managerDao);
    }

    @Override
    public int deleteBatch(List<JobCategory> list) {
        int count=0;
        for (JobCategory jobCategory:list){
            jobCategory.setDelFlag(true);
            int i=jobCategoryDao.updateByPrimaryKeySelective(jobCategory);
            count=count+i;
        }
        return count;
    }

    @Override
    public List<JobCategory> findCategoryTree(Long parentId) {
        Example example = new Example(JobCategory.class);
        Example.Criteria createCriteria = example.createCriteria();
        createCriteria.andEqualTo("parentId",parentId).andEqualTo("delFlag",false);
        return jobCategoryDao.selectByExample(example);
      //  return managerDao.findCategeoryTree(parentId);
    }

    @Override
    public int addRecruitRegion(RecruitRegion recruitRegion) {
        recruitRegion.setCreateTime(new Timestamp(System.currentTimeMillis()));
        recruitRegion.setDelFlag(false);
        return recruitRegionDao.insertUseGeneratedKeys(recruitRegion);
    }

    @Override
    public PageResult findRecruitRegionPage(PageRequest pageRequest) {
        return MybatisPageHelper.findPage(pageRequest,managerDao,"findRegionPage");
    }

    @Override
    public int editRegion(RecruitRegion recruitRegion) {
        return recruitRegionDao.updateByPrimaryKeySelective(recruitRegion);
    }

    @Override
    public int regionMove(List<RecruitRegion> list) {
        int count=0;
        for (RecruitRegion recruitRegion:list){
            recruitRegion.setDelFlag(true);
            int i=recruitRegionDao.updateByPrimaryKeySelective(recruitRegion);
            count=count+i;
        }
        return count;
    }

    @Override
    public List<RecruitRegion> findAllRegion() {
        return recruitRegionDao.selectAll();
    }
}
