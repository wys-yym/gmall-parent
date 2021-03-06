package com.atguigu.gmall.product.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.model.entity.list.SearchAttr;
import com.atguigu.gmall.model.entity.product.*;
import com.atguigu.gmall.product.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.product.controller
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/2 13:20
 * @Version: 1.0
 */
@RestController
@RequestMapping("api/product")
public class ProductApiController {
    @Autowired
    SkuService skuService;

    @Autowired
    SpuService spuService;

    @Autowired
    BaseCategoryService baseCategoryService;

    @Autowired
    BaseTrademarkService baseTrademarkService;

    @Autowired
    BaseAttrService baseAttrService;

    @RequestMapping("getPrice/{skuId}")
    BigDecimal getPrice(@PathVariable Long skuId) {
        BigDecimal price = skuService.getPrice(skuId);
        return price;
    }

    @RequestMapping("getSkuInfo/{skuId}")
    SkuInfo getSkuInfo(@PathVariable Long skuId) {
        SkuInfo skuInfo = skuService.getSkuInfo(skuId);
        return skuInfo;
    }

    @RequestMapping("getSpuSaleAttrList/{spuId}/{skuId}")
    List<SpuSaleAttr> getSpuSaleAttrList(@PathVariable Long spuId,@PathVariable Long skuId) {
        List<SpuSaleAttr> spuSaleAttrList = spuService.getSpuSaleAttrList(spuId,skuId);
        return spuSaleAttrList;
    }

    @RequestMapping("getCategoryView/{category3Id}")
    BaseCategoryView getCategoryView(@PathVariable Long category3Id) {
        BaseCategoryView baseCategoryView = baseCategoryService.getCategoryView(category3Id);
        return baseCategoryView;
    }

    @RequestMapping("getSaleAttrValuesBySpu/{spuId}")
    Map<String, Long> getSaleAttrValuesBySpu(@PathVariable Long spuId) {
        Map<String, Long> map = spuService.getSaleAttrValuesBySpu(spuId);
        return map;
    }

    @RequestMapping("getBaseCategoryList")
    List<JSONObject> getBaseCategoryList(){
        List<JSONObject> list = baseCategoryService.getBaseCategoryList();
        return list;
    }

    @RequestMapping("getTradeMarkByTmId/{TmId}")
    BaseTrademark getTradeMarkByTmId(@PathVariable Long TmId){
        BaseTrademark baseTrademark = baseTrademarkService.getTradeMarkByTmId(TmId);
        return baseTrademark;
    }

    @RequestMapping("getSearchAttrListBySkuId/{skuId}")
    List<SearchAttr> getSearchAttrListBySkuId(@PathVariable Long skuId){
        List<SearchAttr> searchAttrList = baseAttrService.getSearchAttrListBySkuId(skuId);
        return searchAttrList;
    }
}
