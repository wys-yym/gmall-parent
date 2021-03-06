package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.model.entity.product.BaseAttrInfo;
import com.atguigu.gmall.model.entity.product.BaseAttrValue;
import com.atguigu.gmall.model.entity.product.BaseSaleAttr;
import com.atguigu.gmall.product.service.BaseAttrService;
import com.atguigu.gmall.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.product.controller
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/11/28 16:22
 * @Version: 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("admin/product")
public class BaseAttrApiController {
    @Autowired
    private BaseAttrService baseAttrService;

    @RequestMapping("attrInfoList/{category1Id}/{category2Id}/{category3Id}")
    public Result attrInfoList(@PathVariable("category3Id") String category3Id) {
        List<BaseAttrInfo> baseAttrInfoList = baseAttrService.attrInfoList(category3Id);
        return Result.ok(baseAttrInfoList);
    }

    @RequestMapping("saveAttrInfo")
    public Result saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo) {
        baseAttrService.saveAttrInfo(baseAttrInfo);
        return Result.ok();
    }

    @RequestMapping("getAttrValueList/{attrId}")
    public Result getAttrValueList(@PathVariable Long attrId) {
        List<BaseAttrValue> baseAttrValueList = baseAttrService.getAttrValueList(attrId);
        return Result.ok(baseAttrValueList);
    }

    @RequestMapping("baseSaleAttrList")
    public Result baseSaleAttrList() {
        List<BaseSaleAttr> baseSaleAttrList = baseAttrService.baseSaleAttrList();
        return Result.ok(baseSaleAttrList);
    }
}
