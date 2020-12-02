package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.model.entity.product.BaseCategoryView;
import com.atguigu.gmall.model.entity.product.SkuImage;
import com.atguigu.gmall.model.entity.product.SkuInfo;
import com.atguigu.gmall.model.entity.product.SpuSaleAttr;
import com.atguigu.gmall.product.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

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

    @RequestMapping("getSpuSaleAttrList/{skuId}")
    List<SpuSaleAttr> getSpuSaleAttrList(@PathVariable Long skuId) {
        List<SpuSaleAttr> spuSaleAttrList = skuService.getSpuSaleAttrList(skuId);
        return spuSaleAttrList;
    }

    @RequestMapping("getCategoryView/{category3Id}")
    BaseCategoryView getCategoryView(@PathVariable Long category3Id) {
        BaseCategoryView baseCategoryView = skuService.getCategoryView(category3Id);
        return baseCategoryView;
    }
}
