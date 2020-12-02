package com.atguigu.gmall.item.service;

import java.util.Map;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.item.service
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/2 13:05
 * @Version: 1.0
 */
public interface ItemService {
    /**
     * 获取商品详情
     * @Author WangYongShuai
     * @Date 13:05 2020/12/2
     * @param skuId
     * @throws
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String, Object> getItem(Long skuId);
}
