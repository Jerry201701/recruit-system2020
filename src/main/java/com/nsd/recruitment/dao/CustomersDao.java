package com.nsd.recruitment.dao;

import java.util.List;

import com.github.pagehelper.Page;
import com.nsd.recruitment.domain.Customers;
import com.nsd.recruitment.tkmapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
@Mapper
@Component
public interface CustomersDao extends MyMapper<Customers> {
 @Select("select id,name,phone,email,school,education,profession,profile from customers")
 List<Customers> findAll();
 Customers getOneById(Long id);
 //Customers getOneByName(String name);
 Page<Customers>findByPage();
 
}
