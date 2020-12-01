package com.atguigu.gmall.all;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.all
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/1 23:06
 * @Version: 1.0
 */
@SpringBootApplication
@ComponentScan("com.atguigu.gmall")
public class WebAllApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebAllApplication.class,args);
    }
}
