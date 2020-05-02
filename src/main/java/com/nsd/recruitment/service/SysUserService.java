package com.nsd.recruitment.service;



import com.nsd.recruitment.domain.SysUser;
import com.nsd.recruitment.domain.SysUserRole;

import java.util.List;
import java.util.Set;

/**
 * 用户管理
 * @author Louis
 * @date Oct 29, 2018
 */
public interface SysUserService extends CurdService<SysUser> {

	SysUser findByName(String username);

	/**
	 * 查找用户的菜单权限标识集合
	 * @param userName
	 * @return
	 */
	Set<String> findPermissions(String userName);

	/**
	 * 查找用户的角色集合
	 * @return
	 */

	List<SysUserRole> findUserRoles(Long userId);


	SysUser selectByPrimaryKey(Long id);





}
