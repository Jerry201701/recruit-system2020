package com.nsd.recruitment.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.nsd.recruitment.common.PageRequest;
import com.nsd.recruitment.dao.ManagerDao;
import com.nsd.recruitment.domain.ManagerInfo;
import com.nsd.recruitment.service.ManagerService;
import com.nsd.recruitment.vo.JobCategoryRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerDao managerDao;
    @Override
    public Long saveManagerInfo(ManagerInfo managerInfo) {
        managerInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
        if (managerDao.saveManagerInfo(managerInfo)==1){
            return managerInfo.getId();
        }
        return 0L;
    }

    @Override
    public ManagerInfo getOneById(Long id) {
        return managerDao.getOneById(id);
    }

    @Override
    public Integer findManagerStatus(Long companyId, Long userId) {
        return managerDao.findManagerStatus(companyId,userId);
    }

    @Override
    public List<ManagerInfo> listManagerByCompany(Long companyId) {
        return managerDao.listManagerByCompany(companyId);
    }

    @Override
    public int approvesManager(ManagerInfo managerInfo) {
        return managerDao.approvesManager(managerInfo);
    }
}
