package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.result.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.product.controller
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/11/27 19:35
 * @Version: 1.0
 */
@RestController
@RequestMapping("admin/product")
public class CategoryApiController {

    @RequestMapping("getCategory1")
    public Result getCategory1(){
        return Result.ok();
    }
}
