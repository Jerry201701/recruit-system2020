package com.nsd.recruitment;


import com.nsd.recruitment.handler.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Spring Boot 应用启动类
 *
 * Created by bysocket on 16/4/26.
 */

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan("org.spring.springboot.dao")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
//        try {
//            new NettyServer(8004).start();
//        }catch(Exception e) {
//            System.out.println("NettyServerError:"+e.getMessage());
//        }

        System.out.println("service setup successfully!!!!");

    }
}
