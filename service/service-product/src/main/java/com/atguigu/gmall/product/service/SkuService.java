package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.entity.product.BaseCategoryView;
import com.atguigu.gmall.model.entity.product.SkuInfo;
import com.atguigu.gmall.model.entity.product.SpuSaleAttr;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.math.BigDecimal;
import java.util.List;

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

    /**
     * 获取商品价格
     * @Author WangYongShuai
     * @Date 18:44 2020/12/2
     * @param skuId
     * @throws
     * @return java.math.BigDecimal
     */
    BigDecimal getPrice(Long skuId);

    /**
     * 获取sku信息
     * @Author WangYongShuai
     * @Date 18:55 2020/12/2
     * @param skuId
     * @throws
     * @return java.util.List<com.atguigu.gmall.model.entity.product.SkuImage>
     */
    SkuInfo getSkuInfo(Long skuId);

    /**
     * 获取spu销售属性
     * @Author WangYongShuai
     * @Date 19:47 2020/12/2
     * @param skuId
     * @throws
     * @return java.util.List<com.atguigu.gmall.model.entity.product.SpuSaleAttr>
     */
    List<SpuSaleAttr> getSpuSaleAttrList(Long skuId);
}
