package com.atguigu.gmall.item.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.item.client
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/2 11:44
 * @Version: 1.0
 */
@FeignClient("service-item")
public interface ItemFeignClient {
    /**
     * 获取商品详情
     * @Author WangYongShuai
     * @Date 11:52 2020/12/2
     * @param skuId
     * @throws
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping("api/item/getItem/{skuId}")
    Map<String, Object> getItem(@PathVariable Long skuId);
}
