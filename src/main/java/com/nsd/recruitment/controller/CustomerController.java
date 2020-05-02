package com.nsd.recruitment.controller;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nsd.recruitment.common.HttpResult;
import com.nsd.recruitment.domain.Customers;
import com.nsd.recruitment.dto.query.CustomerQuery;
import com.nsd.recruitment.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	@Autowired
    private CustomerService customerService;

	
	@GetMapping("/all")
	public List<Customers> findAll(){
		return customerService.findAllCustomers();
	}
	@RequestMapping("/getOneByName")
	public List<Customers> getBrandByName(Customers customer){
		return customerService.getCustomersByName(customer);
	}
	
	@GetMapping("/getOneById/{id}")
	public Customers getOneById(@PathVariable("id") Long id){
		return customerService.getCustomerById(id);
	}
	//@PostMapping("/add")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public int saveCustomer(@RequestBody Customers customer) {
		System.out.println(customer.toString());
		return customerService.addCustomers(customer);
	}
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public int updateCustomer(@RequestBody Customers customer) {
		return customerService.updateCustomer(customer);
	}
	@PostMapping("/move")
	@ResponseStatus(HttpStatus.OK)
	public int moveCustomers(@RequestBody CustomerQuery customerQuery){
	//	System.out.print(id);
		System.out.print(customerQuery.getId());
		//return customerService.moveCustomers(customers);
		return  0;
	}
	@PostMapping("/conditions")
	@ResponseStatus(HttpStatus.OK)
	public List<Customers> findCustomersConditions(@RequestBody CustomerQuery customerQuery){
		//org.springframework.beans.BeanUtils.copyProperties(sourceDemo, targetDemo);
		Customers customers=new Customers();
		BeanUtils.copyProperties(customerQuery,customers);

		System.out.println(customerQuery.getName());
		List<Customers>list=customerService.queryOnConditions(customers);
		return list;

	}
	@PostMapping("pages")
	@ResponseStatus(HttpStatus.OK)
	public PageInfo<Customers> findByPage(Integer pageNum, Integer pageSize){
		PageInfo<Customers> pageInfo=new PageInfo<>(customerService.findByPage(pageNum,pageSize));
		return pageInfo;

	}



	
}
