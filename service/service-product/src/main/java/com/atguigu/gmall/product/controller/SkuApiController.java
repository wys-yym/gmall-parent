package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.model.entity.product.SkuInfo;
import com.atguigu.gmall.product.service.SkuService;
import com.atguigu.gmall.result.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.product.controller
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/1 18:42
 * @Version: 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("admin/product")
public class SkuApiController {
    @Autowired
    private SkuService skuService;

    @RequestMapping("saveSkuInfo")
    public Result saveSkuInfo(@RequestBody SkuInfo skuInfo) {
        skuService.saveSkuInfo(skuInfo);
        return Result.ok();
    }

    @RequestMapping("list/{pageNum}/{pageSize}")
    public Result skuList(@PathVariable("pageNum") Long pageNum,
                          @PathVariable("pageSize") Long pageSize) {
        Page<SkuInfo> page = new Page<>(pageNum, pageSize);
        skuService.skuList(page);
        return Result.ok(page);
    }

    @RequestMapping("onSale/{skuId}")
    public Result onSale(@PathVariable Long skuId){
        skuService.onSale(skuId);
        return Result.ok();
    }

    @RequestMapping("cancelSale/{skuId}")
    public Result cancelSale(@PathVariable Long skuId){
        skuService.cancelSale(skuId);
        return Result.ok();
    }
}
