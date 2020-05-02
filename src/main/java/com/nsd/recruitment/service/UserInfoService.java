package com.nsd.recruitment.service;

import com.github.pagehelper.Page;
import com.nsd.recruitment.domain.UserInfo;
import com.nsd.recruitment.vo.CompanyBasicRespVo;

public interface UserInfoService {
    Long saveUserInfo(UserInfo userInfo);
    int updateUserInfo(UserInfo userInfo);
    UserInfo getOneById(Long id);
    Page<UserInfo> findByPage();
    UserInfo findUserByopenid(String openid);
    CompanyBasicRespVo companyBasicByOpenid(String openid);
    int weiUserInfo(UserInfo userInfo);

}
