package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.entity.product.SkuInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.product.service
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/1 18:44
 * @Version: 1.0
 */
public interface SkuService {
    /**
     * 添加sku信息
     * @Author WangYongShuai
     * @Date 19:06 2020/12/1
     * @param skuInfo
     * @throws
     * @return void
     */
    void saveSkuInfo(SkuInfo skuInfo);

    /**
     * sku信息分页
     * @Author WangYongShuai
     * @Date 19:28 2020/12/1
     * @param page
     * @throws
     * @return void
     */
    void skuList(Page<SkuInfo> page);

    /**
     * 上架
     * @Author WangYongShuai
     * @Date 19:34 2020/12/1
     * @param skuId
     * @throws
     * @return void
     */
    void onSale(Long skuId);

    /**
     * 下架
     * @Author WangYongShuai
     * @Date 19:38 2020/12/1
     * @param skuId
     * @throws
     * @return void
     */
    void cancelSale(Long skuId);
}
