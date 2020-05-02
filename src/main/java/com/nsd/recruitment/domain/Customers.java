package com.nsd.recruitment.domain;

import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "customers")
public class Customers implements Serializable{

	/**
	 * 当实体字段命名和数据库不一致的时候，用注解column指定对应关系，便于tkmapper映射
	 */
	private static final long serialVersionUID = -4125499586805684021L;
	/**
	表id
	 */
	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;
	/**
	名字
	 */
	private String name;
	/**
	电话号码
	 */
	private String phone;
	/**
	邮箱
	 */
	private String email;
	/**
	学校
	 */
	private String school;
	/**
	学历
	 */
	private String education;
	/**
	简介
	 */
	private String profile;
	/**
	工作
	 */
	private String profession;
	/**
	 * 创建时间

	 */
	private Timestamp  createdTime;

	/**
	 * 修改时间
	 * @return
	 */
	private Timestamp updatedTime;

	/**
	 * 创建人
	 * @return
	 */
	private String createdBy;

	/**
	 * 修改人
	 * @return
	 */
	private String updatedBy;



/*

	public Customers() {
		super();
	}

	public Customers(Integer id, String name, String phone, String email, String school, String education,
			String profile,String profession) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.school = school;
		this.education = education;
		this.profile = profile;
		this.profession=profession;
	}
*/

	@Override
	public String toString() {
		return "Customers [id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + "]";
	}
	

}
