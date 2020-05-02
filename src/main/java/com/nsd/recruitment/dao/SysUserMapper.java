package com.nsd.recruitment.dao;

import com.nsd.recruitment.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    
    List<SysUser> findPage();
    
    SysUser findByName(@Param(value = "name") String name);

	List<SysUser> findPageByName(@Param(value = "name") String name);

	List<SysUser> findPageByNameAndEmail(@Param(value = "name") String name, @Param(value = "email") String email);
    Long findRegionByUser(@Param(value = "userId") Long userId);
    int changePassword(@Param(value = "password") String password, @Param(value = "salt") String salt, @Param(value = "userId") Long userId);
    String findPasswordById(@Param(value = "userId") Long userId);
    SysUser findByMobilAndType(@Param(value = "mobile") String mobile, @Param(value = "registerType") Integer registerType);
    List<Long> findDeliveryByKeyWord(@Param(value = "keyWord") String keyWord);


}