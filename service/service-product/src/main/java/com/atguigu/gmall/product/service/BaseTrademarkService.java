package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.entity.product.BaseTrademark;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

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
     * 获取品牌集合
     * @Author WangYongShuai
     * @Date 12:58 2020/11/30
     * @param
     * @throws
     * @return java.util.List<com.atguigu.gmall.model.entity.product.BaseTrademark>
     */
    List<BaseTrademark> getTrademarkList();

    /**
     * 分页查询商品品牌
     * @Author WangYongShuai
     * @Date 19:44 2020/12/1
     * @param page
     * @throws
     * @return void
     */
    void baseTrademark(Page<BaseTrademark> page);
}
