package com.nsd.recruitment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class RedisTestController {
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping(value = "/redis/save")
    public String redisTest(){
       // System.out.println(redisTemplate.opsForHash().entries("sessionKey"));
      //  redisTemplate.opsForHash().delete("sessionKey","openid01");
     //   System.out.println(redisTemplate.opsForHash().entries("sessionKey"));
       redisTemplate.opsForHash().put("sessionKey","openid01","asddfff");


        redisTemplate.opsForHash().put("sessionKey","openid02","bbbbbb");
//        redisTemplate.opsForHash().put("sessionKey","openid03","cccccc");
//        redisTemplate.opsForHash().put("sessionKey","openid04","ffffffff");
        redisTemplate.expire("sessionKey", 60, TimeUnit.SECONDS);


        return "success";
    }
    @RequestMapping(value = "/get/value")
    public String redisValue(){
        System.out.println(redisTemplate.opsForHash().entries("sessionKey").get("openid01"));


        return "success";
    }
}
