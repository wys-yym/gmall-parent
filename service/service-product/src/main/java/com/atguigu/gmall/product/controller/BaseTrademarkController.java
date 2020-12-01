package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.model.entity.product.BaseTrademark;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.atguigu.gmall.result.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * @Date: 2020/11/30 12:53
 * @Version: 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("admin/product/baseTrademark")
public class BaseTrademarkController {
    @Autowired
    private BaseTrademarkService baseTrademarkService;

    @RequestMapping("getTrademarkList")
    public Result getTrademarkList() {
        List<BaseTrademark> baseTrademarkList = baseTrademarkService.getTrademarkList();
        return Result.ok(baseTrademarkList);
    }

    @RequestMapping("{pageNum}/{pageSize}")
    public Result baseTrademark(@PathVariable Long pageNum,
                                @PathVariable Long pageSize) {
        Page<BaseTrademark> page = new Page<>(pageNum,pageSize);
        baseTrademarkService.baseTrademark(page);
        return Result.ok(page);
    }
}
