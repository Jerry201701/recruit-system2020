package com.nsd.recruitment.service.impl;

import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.nsd.recruitment.dao.CustomersDao;
import com.nsd.recruitment.domain.Customers;
import com.nsd.recruitment.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service
public class CustomersServiceImpl implements CustomerService {
	@Autowired
	private CustomersDao dao;
	@Override
	public List<Customers> findAllCustomers() {

		return dao.findAll();
	}

	@Override
	public Customers getCustomerById(Long id) {

		return dao.getOneById(id);
	}
/*
 * 内置通用标准化查询
 * 
 */
	@Override
	public List<Customers> getCustomersByName(Customers customer) {
		Example example = new Example(Customers.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo(customer);
		//return  dao.selectByExample(example);
		return  dao.selectByExample(example);
		
	}

@Override
public int addCustomers(Customers customer) {
	int i=dao.insert(customer);
	return i;
}

@Override
public int updateCustomer(Customers customer) {
	
	Example example = new Example(Customers.class);
	example.createCriteria().andEqualTo("name",customer.getName());
	//Customers record = new Customers();
	//record.setName(customer.getName());
	
	
	return dao.updateByExampleSelective(customer, example);
}

	@Override
	public int moveCustomers(Customers customers) {
		return dao.delete(customers);
	}

	@Override
	public List<Customers> queryOnConditions(Customers customer) {
		Example example = new Example(Customers.class);
		example.createCriteria().andEqualTo("name",customer.getName())
							     .andEqualTo("phone",customer.getPhone())
				                 .andEqualTo("email",customer.getEmail());
		return dao.selectByExample(example);
	}

	@Override
	public Page<Customers> findByPage(int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return dao.findByPage();
	}
}
