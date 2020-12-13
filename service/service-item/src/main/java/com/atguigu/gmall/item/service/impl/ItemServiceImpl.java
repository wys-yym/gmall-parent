package com.atguigu.gmall.item.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.config.ThreadPoolConfig;
import com.atguigu.gmall.item.service.ItemService;
import com.atguigu.gmall.list.client.ListFeignClient;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;
import java.util.function.Supplier;

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

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    ListFeignClient listFeignClient;

    @Override
    public Map<String, Object> getItem(Long skuId) {
        HashMap<String, Object> resultMap = getItemByMultiThread(skuId);
        //设置热度值
        listFeignClient.hotScore(skuId);
        return resultMap;
    }

    private HashMap<String, Object> getItemByMultiThread(Long skuId) {
        HashMap<String, Object> resultMap = new HashMap<>();
        CompletableFuture<SkuInfo> skuInfoCompletableFuture = CompletableFuture.supplyAsync(new Supplier<SkuInfo>() {
            @Override
            public SkuInfo get() {
                SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
                resultMap.put("skuInfo", skuInfo);
                return skuInfo;
            }
        }, threadPoolExecutor);
        CompletableFuture<Void> priceCompletableFuture = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                BigDecimal price = productFeignClient.getPrice(skuId);
                resultMap.put("price", price);
            }
        }, threadPoolExecutor);
        CompletableFuture<Void> spuSaleAttrsCompletableFuture = skuInfoCompletableFuture.thenAcceptAsync(new Consumer<SkuInfo>() {
            @Override
            public void accept(SkuInfo skuInfo) {
                List<SpuSaleAttr> spuSaleAttrList = productFeignClient.getSpuSaleAttrList(skuInfo.getSpuId(), skuId);
                resultMap.put("spuSaleAttrList", spuSaleAttrList);
                resultMap.put("spuSaleAttrList", spuSaleAttrList);
            }
        }, threadPoolExecutor);
        CompletableFuture<Void> categoryViewCompletableFuture = skuInfoCompletableFuture.thenAcceptAsync(new Consumer<SkuInfo>() {
            @Override
            public void accept(SkuInfo skuInfo) {
                BaseCategoryView categoryView = productFeignClient.getCategoryView(skuInfo.getCategory3Id());
                resultMap.put("categoryView", categoryView);
            }
        }, threadPoolExecutor);
        CompletableFuture<Void> valuesSkuJsonCompletableFuture = skuInfoCompletableFuture.thenAcceptAsync(new Consumer<SkuInfo>() {
            @Override
            public void accept(SkuInfo skuInfo) {
                Map<String, Long> jsonMap = productFeignClient.getSaleAttrValuesBySpu(skuInfo.getSpuId());
                String json = JSON.toJSONString(jsonMap);
                resultMap.put("valuesSkuJson", json);
            }
        }, threadPoolExecutor);
        //组合线程,等待所有线程执行结束后才执行主线程
        CompletableFuture.allOf(skuInfoCompletableFuture, priceCompletableFuture, spuSaleAttrsCompletableFuture,
                categoryViewCompletableFuture, valuesSkuJsonCompletableFuture).join();
        return resultMap;
    }
}
