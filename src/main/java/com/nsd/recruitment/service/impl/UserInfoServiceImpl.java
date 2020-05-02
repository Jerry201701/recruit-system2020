package com.nsd.recruitment.service.impl;

import com.github.pagehelper.Page;
import com.nsd.recruitment.dao.UserInfoDao;
import com.nsd.recruitment.domain.UserInfo;
import com.nsd.recruitment.service.UserInfoService;
import com.nsd.recruitment.vo.CompanyBasicRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public Long saveUserInfo(UserInfo userInfo) {
        Timestamp present = Timestamp.valueOf(LocalDateTime.now());
        userInfo.setCreateTime(present);
        userInfoDao.saveUserInfo(userInfo);
        return userInfo.getId();

    }

    @Override
    public int updateUserInfo(UserInfo userInfo) {
        Timestamp present = Timestamp.valueOf(LocalDateTime.now());
        userInfo.setUpdateTime(present);
        return userInfoDao.updateUserInfo(userInfo);
    }

    @Override
    public UserInfo getOneById(Long id) {
        return userInfoDao.getOneById(id);
    }

    @Override
    public Page<UserInfo> findByPage() {
        return userInfoDao.findUserByPage();
    }

    @Override
    public UserInfo findUserByopenid(String openid) {
        return userInfoDao.findUserByopenid(openid);
    }

    @Override
    public CompanyBasicRespVo companyBasicByOpenid(String openid) {
        return userInfoDao.companyBasicByOpenid(openid);
    }

    @Override
    public int weiUserInfo(UserInfo userInfo) {
        return userInfoDao.weiUserInfo(userInfo);
    }
}
