package com.atguigu.gmall.list.service;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.model.entity.list.SearchParam;
import com.atguigu.gmall.model.entity.list.SearchResponseVo;

import java.util.List;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.list.service
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/7 23:04
 * @Version: 1.0
 */
public interface ListService {
    /**
     * 获取首页的分类集合
     * @Author WangYongShuai
     * @Date 23:06 2020/12/7
     * @param
     * @throws
     * @return java.util.List<com.alibaba.fastjson.JSONObject>
     */
    List<JSONObject> getBaseCategoryList();

    /**
     * 上架，同步到搜索引擎
     * @Author WangYongShuai
     * @Date 21:33 2020/12/10
     * @param skuId
     * @throws
     * @return void
     */
    void onSale(Long skuId);

    /**
     * 下架，从搜索引擎清除
     * @Author WangYongShuai
     * @Date 21:33 2020/12/10
     * @param skuId
     * @throws
     * @return void
     */
    void cancelSale(Long skuId);

    /**
     * 创建商品的索引库
     * @Author WangYongShuai
     * @Date 23:36 2020/12/10
     * @param
     * @throws
     * @return void
     */
    void createGoodsIndex();

    /**
     * 获取搜索请求所响应的结果
     * @Author WangYongShuai
     * @Date 14:21 2020/12/11
     * @param searchParam
     * @throws
     * @return com.atguigu.gmall.model.entity.list.SearchResponseVo
     */
    SearchResponseVo list(SearchParam searchParam);
}
