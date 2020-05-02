package com.nsd.recruitment.dao;

import com.github.pagehelper.Page;
import com.nsd.recruitment.domain.UserInfo;
import com.nsd.recruitment.vo.ApplicantRespVo;
import com.nsd.recruitment.vo.CompanyBasicRespVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserInfoDao {
    int saveUserInfo(UserInfo userInfo);
    int updateUserInfo(UserInfo userInfo);
    UserInfo getOneById(Long id);
    Page<UserInfo> findUserByPage();
    UserInfo getUserByPhone(@Param(value = "phone") String phone);
    UserInfo findUserByopenid(@Param(value = "openid")String openid);
    ApplicantRespVo showApplicantBasicByOpenid(@Param(value = "openid")String openid);
    CompanyBasicRespVo companyBasicByOpenid(@Param(value = "openid")String openid);
    int weiUserInfo(UserInfo userInfo);

}
