package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.model.entity.product.BaseCategory1;
import com.atguigu.gmall.model.entity.product.BaseCategory2;
import com.atguigu.gmall.model.entity.product.BaseCategory3;
import com.atguigu.gmall.product.service.BaseCategoryService;
import com.atguigu.gmall.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.product.controller
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/11/27 19:35
 * @Version: 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("admin/product")
public class BaseCategoryApiController {
    @Autowired
    private BaseCategoryService baseCategoryService;

    @RequestMapping("getCategory1")
    public Result getCategory1() {
        List<BaseCategory1> baseCategory1List = baseCategoryService.getCategory1();
        return Result.ok(baseCategory1List);
    }

    @RequestMapping("getCategory2/{category1Id}")
    public Result getCategory2(@PathVariable("category1Id") Long category1Id) {
        List<BaseCategory2> baseCategory2List = baseCategoryService.getCategory2(category1Id);
        return Result.ok(baseCategory2List);
    }

    @RequestMapping("getCategory3/{category2Id}")
    public Result getCategory3(@PathVariable("category2Id") Long category2Id) {
        List<BaseCategory3> baseCategory3List = baseCategoryService.getCategory3(category2Id);
        return Result.ok(baseCategory3List);
    }

}
