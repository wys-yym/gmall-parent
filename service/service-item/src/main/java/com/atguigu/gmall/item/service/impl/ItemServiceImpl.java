package com.atguigu.gmall.item.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.item.service.ItemService;
import com.atguigu.gmall.model.entity.product.BaseCategoryView;
import com.atguigu.gmall.model.entity.product.SkuImage;
import com.atguigu.gmall.model.entity.product.SkuInfo;
import com.atguigu.gmall.model.entity.product.SpuSaleAttr;
import com.atguigu.gmall.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.item.service.impl
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/2 13:07
 * @Version: 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ProductFeignClient productFeignClient;

    @Override
    public Map<String, Object> getItem(Long skuId) {
        HashMap<String, Object> resultMap = new HashMap<>();
        BigDecimal price = productFeignClient.getPrice(skuId);
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        List<SpuSaleAttr> spuSaleAttrList = productFeignClient.getSpuSaleAttrList(skuInfo.getSpuId(),skuId);
        BaseCategoryView categoryView = productFeignClient.getCategoryView(skuInfo.getCategory3Id());
        Map<String,Long> jsonMap = productFeignClient.getSaleAttrValuesBySpu(skuInfo.getSpuId());
        resultMap.put("price",price);
        resultMap.put("skuInfo",skuInfo);
        resultMap.put("spuSaleAttrList",spuSaleAttrList);
        resultMap.put("categoryView",categoryView);
        String json = JSON.toJSONString(jsonMap);
        resultMap.put("valuesSkuJson",json);
        return resultMap;
    }
}
