package com.nsd.recruitment.dto.query;

import lombok.Data;
import java.io.Serializable;
@Data
public class CustomerQuery implements Serializable {

    private static final long serialVersionUID = -1836119196058281391L;
    /**
     * id
     */
    private  Long id;
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
  //  private String school;
    /**
     学历
     */
  ///  private String education;
    /**
     简介
     */
  //  private String profile;
    /**
     工作
     */
 //   private String profession;





}
