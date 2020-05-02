package com.nsd.recruitment.dao;

import com.nsd.recruitment.domain.Customers;
import com.nsd.recruitment.domain.JobCategory;
import com.nsd.recruitment.tkmapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface JobCategoryDao extends MyMapper<JobCategory> {
}
