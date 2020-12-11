package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.entity.list.SearchAttr;
import com.atguigu.gmall.model.entity.product.BaseAttrInfo;
import com.atguigu.gmall.model.entity.product.BaseAttrValue;
import com.atguigu.gmall.model.entity.product.BaseSaleAttr;

import java.util.List;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.product.service
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/11/28 21:20
 * @Version: 1.0
 */
public interface BaseAttrService {

    /**
     * 通过分类Id获取商品属性信息
     * @Author WangYongShuai
     * @Date 21:26 2020/11/28
     * @param category3Id
     * @throws
     * @return java.util.List<com.atguigu.gmall.model.entity.product.BaseAttrInfo>
     */
    List<BaseAttrInfo> attrInfoList(String category3Id);

    /**
     * 保存商品属性信息
     * @Author WangYongShuai
     * @Date 22:47 2020/11/28
     * @param baseAttrInfo
     * @throws
     * @return void
     */
    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    /**
     * 通过商品属性Id获取所有对应的属性值信息
     * @Author WangYongShuai
     * @Date 22:48 2020/11/28
     * @param attrId
     * @throws
     * @return java.util.List<com.atguigu.gmall.model.entity.product.BaseAttrValue>
     */
    List<BaseAttrValue> getAttrValueList(Long attrId);

    /**
     * 获取所有平台销售属性
     * @Author WangYongShuai
     * @Date 13:05 2020/11/30
     * @param
     * @throws
     * @return java.util.List<com.atguigu.gmall.model.entity.product.BaseSaleAttr>
     */
    List<BaseSaleAttr> baseSaleAttrList();

    /**
     * 过去搜索属性信息
     * @Author WangYongShuai
     * @Date 11:41 2020/12/11
     * @param skuId
     * @throws
     * @return java.util.List<com.atguigu.gmall.model.entity.list.SearchAttr>
     */
    List<SearchAttr> getSearchAttrListBySkuId(Long skuId);
}
