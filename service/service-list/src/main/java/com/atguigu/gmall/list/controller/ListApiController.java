package com.atguigu.gmall.list.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.list.service.ListService;
import com.atguigu.gmall.model.entity.list.SearchParam;
import com.atguigu.gmall.model.entity.list.SearchResponseVo;
import com.atguigu.gmall.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.list.controller
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/7 23:01
 * @Version: 1.0
 */
@RestController
@RequestMapping("api/list")
public class ListApiController {
    @Autowired
    ListService listService;

    @RequestMapping("getBaseCategoryList")
    List<JSONObject> getBaseCategoryList(){
        List<JSONObject> list = listService.getBaseCategoryList();
        return list;
    }

    @RequestMapping("onSale/{skuId}")
    void onSale(@PathVariable("skuId") Long skuId){
        listService.onSale(skuId);
        System.out.println("上架，同步到搜索引擎");
    }

    @RequestMapping("cancelSale/{skuId}")
    void cancelSale(@PathVariable("skuId") Long skuId){
        listService.cancelSale(skuId);
        System.out.println("下架，从搜索引擎移除");
    }

    @RequestMapping("createGoodsIndex")
    Result createGoodsIndex() {
        listService.createGoodsIndex();
        return Result.ok();
    }

    @RequestMapping("list")
    SearchResponseVo list(@RequestBody SearchParam searchParam){
        SearchResponseVo searchResponseVo = listService.list(searchParam);
        return searchResponseVo;
    }
}
