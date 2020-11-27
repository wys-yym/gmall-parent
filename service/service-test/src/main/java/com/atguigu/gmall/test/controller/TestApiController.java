package com.atguigu.gmall.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.test.controller
 * @Author: WangYongShuai
 * @Description: 测试API
 * @Date: 2020/11/27 18:57
 * @Version: 1.0
 */
@RestController
@RequestMapping("api/test")
public class TestApiController {
    @RequestMapping("test")
    public String test() {
        return "test";
    }
}
