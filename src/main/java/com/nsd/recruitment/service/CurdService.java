package com.nsd.recruitment.service;



import com.nsd.recruitment.common.PageRequest;
import com.nsd.recruitment.common.PageResult;

import java.util.List;

/**
 * 通用CURD接口
 */
public interface CurdService<T> {
	
	/**
	 * 保存操作
	 * @param record
	 * @return
	 */
	int save(T record);
	
	/**
	 * 删除操作
	 * @param record
	 * @return
	 */
	int delete(T record);
	
	/**
	 * 批量删除操作
	 *
	 */
	int delete(List<T> records);
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	T findById(Long id);

	PageResult findPage(PageRequest pageRequest);
	

}