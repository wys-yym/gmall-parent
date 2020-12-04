package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.entity.product.SpuImage;
import com.atguigu.gmall.model.entity.product.SpuInfo;
import com.atguigu.gmall.model.entity.product.SpuSaleAttr;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.product.service
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/11/30 11:36
 * @Version: 1.0
 */
public interface SpuService {
    /**
     * 分页查询spu
     * @Author WangYongShuai
     * @Date 11:39 2020/11/30
     * @param page
     * @param category3Id
     * @throws
     * @return void
     */
    void spuInfoList(Page<SpuInfo> page, Long category3Id);

    /**
     * 添加spu信息
     * @Author WangYongShuai
     * @Date 13:14 2020/11/30
     * @param spuInfo
     * @throws
     * @return void
     */
    void saveSpuInfo(SpuInfo spuInfo);

    /**
     * 查询所有spu图片
     * @Author WangYongShuai
     * @Date 16:06 2020/12/1
     * @param spuId
     * @throws
     * @return java.util.List<com.atguigu.gmall.model.entity.product.SpuImage>
     */
    List<SpuImage> spuImageList(Long spuId);

    /**
     * 后台获取所有spu销售属性
     * @Author WangYongShuai
     * @Date 16:15 2020/12/1
     * @param spuId
     * @throws
     * @return java.util.List<com.atguigu.gmall.model.entity.product.SpuSaleAttr>
     */
    List<SpuSaleAttr> spuSaleAttrList(Long spuId);

    /**
     * 前端获取spu销售属性
     * @Author WangYongShuai
     * @Date 15:27 2020/12/4
     * @param spuId
     * @param skuId
     * @throws
     * @return java.util.List<com.atguigu.gmall.model.entity.product.SpuSaleAttr>
     */
    List<SpuSaleAttr> getSpuSaleAttrList(Long spuId, Long skuId);

    /**
     * 通过spuId获取sku与销售属性值的对应关系
     * @Author WangYongShuai
     * @Date 19:14 2020/12/4
     * @param spuId
     * @throws
     * @return java.util.Map<java.lang.String,java.lang.Long>
     */
    Map<String, Long> getSaleAttrValuesBySpu(Long spuId);
}
