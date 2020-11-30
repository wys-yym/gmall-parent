package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.entity.product.BaseTrademark;

import java.util.List;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.product.service
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/11/30 12:55
 * @Version: 1.0
 */
public interface BaseTrademarkService {
    /**
     * 获取商标集合
     * @Author WangYongShuai
     * @Date 12:58 2020/11/30
     * @param
     * @throws
     * @return java.util.List<com.atguigu.gmall.model.entity.product.BaseTrademark>
     */
    List<BaseTrademark> getTrademarkList();
}
