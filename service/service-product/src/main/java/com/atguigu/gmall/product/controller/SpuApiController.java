package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.model.entity.product.SpuInfo;
import com.atguigu.gmall.product.service.SpuService;
import com.atguigu.gmall.result.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.product.controller
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/11/30 11:14
 * @Version: 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("admin/product")
public class SpuApiController {
    @Autowired
    private SpuService spuService;

    @RequestMapping("{pageNum}/{pageSize}")
    public Result spuInfoList(@PathVariable("pageNum") Long pageNum,
                              @PathVariable("pageSize") Long pageSize,
                              Long category3Id){
        Page<SpuInfo> page = new Page<>(pageNum,pageSize);
        spuService.spuInfoList(page,category3Id);
        return Result.ok(page);
    }

    @RequestMapping("saveSpuInfo")
    public Result saveSpuInfo(@RequestBody SpuInfo spuInfo) {
        spuService.saveSpuInfo(spuInfo);
        return Result.ok();
    }
}
