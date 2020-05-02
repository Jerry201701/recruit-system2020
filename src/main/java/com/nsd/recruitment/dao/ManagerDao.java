package com.nsd.recruitment.dao;

import com.github.pagehelper.Page;
import com.nsd.recruitment.domain.ManagerInfo;
import com.nsd.recruitment.domain.RecruitRegion;
import com.nsd.recruitment.vo.JobCategoryRespVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Mapper
public interface ManagerDao {
    int saveManagerInfo(ManagerInfo managerInfo);
    int updateManageInfo(ManagerInfo managerInfo);
    ManagerInfo getOneById(@Param(value = "id")Long id);
    Integer findManagerStatus(@Param(value = "companyId")Long companyId,@Param(value = "userId")Long userId);
    Page<JobCategoryRespVo> findPage();
    List<JobCategoryRespVo> findCategeoryTree(@Param(value = "parentId") Long parentId);
    List<RecruitRegion> findRegionPage();
    Integer countManagerNumber(@Param(value = "companyId")Long companyId);
    List<ManagerInfo>listManagerByCompany(@Param(value = "companyId")Long companyId);
    int approvesManager(ManagerInfo managerInfo);
    ManagerInfo findRecentManagerByCompany(@Param(value = "companyId") Long companyId);
}
