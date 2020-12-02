package com.atguigu.gmall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.product
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/11/27 19:34
 * @Version: 1.0
 */
@SpringBootApplication
@Component("com.atguigu.gmall")
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceProductApplication.class,args);
    }
}
