package com.atguigu.gmall.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.test
 * @Author: WangYongShuai
 * @Description: 启动类
 * @Date: 2020/11/27 18:58
 * @Version: 1.0
 */
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class TestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApiApplication.class,args);
    }
}
