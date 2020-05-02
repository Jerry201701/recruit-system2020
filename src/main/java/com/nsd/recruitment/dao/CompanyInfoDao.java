package com.nsd.recruitment.dao;

import com.github.pagehelper.Page;
import com.nsd.recruitment.tkmapper.MyMapper;
import com.nsd.recruitment.vo.CompanyAndManagerVo;
import com.nsd.recruitment.vo.CompanyCenterVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.nsd.recruitment.domain.CompanyInfo;
import com.nsd.recruitment.domain.Customers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CompanyInfoDao extends MyMapper<CompanyInfo> {
    @Select("select id,name,phone,email,school,education,profession,profile from customers")
    List<CompanyInfo> findAll();
    Customers getOneById(Long id);
    //Customers getOneByName(String name);
    Page<CompanyInfo> findByPage();
    CompanyInfo showCompanyDetail(@Param(value = "code")String code);
    List<CompanyInfo> findCompanyByKeyWord(@Param(value = "keyWord")String keyWord);
    CompanyCenterVo findCenterNumber(@Param(value = "companyId")Long companyId);
    CompanyAndManagerVo companyDetailAndManager(@Param(value = "companyId")Long companyId);
    List<CompanyInfo> findPage();
    List<CompanyInfo> findUncheckPage();
    List<Long>findIdByFullName(@Param(value = "fullName")String fullName);
}
