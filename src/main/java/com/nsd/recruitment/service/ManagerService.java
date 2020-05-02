package com.nsd.recruitment.service;

import com.github.pagehelper.Page;
import com.nsd.recruitment.common.PageRequest;
import com.nsd.recruitment.domain.ManagerInfo;
import com.nsd.recruitment.vo.JobCategoryRespVo;


import java.util.List;

public interface ManagerService {
    Long saveManagerInfo(ManagerInfo managerInfo);
    ManagerInfo getOneById(Long id);
    Integer findManagerStatus(Long companyId,Long userId);
    List<ManagerInfo>listManagerByCompany(Long companyId);
    int approvesManager(ManagerInfo managerInfo);


}
