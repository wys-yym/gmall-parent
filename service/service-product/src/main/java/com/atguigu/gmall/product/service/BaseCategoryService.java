package com.atguigu.gmall.product.service;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.model.entity.product.BaseCategory1;
import com.atguigu.gmall.model.entity.product.BaseCategory2;
import com.atguigu.gmall.model.entity.product.BaseCategory3;
import com.atguigu.gmall.model.entity.product.BaseCategoryView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.product.service
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/11/28 11:45
 * @Version: 1.0
 */
public interface BaseCategoryService {

    /**
     * 获取所有一级分类
     * @Author WangYongShuai
     * @Date 14:26 2020/11/28
     * @param
     * @throws
     * @return java.util.List<com.atguigu.gmall.model.entity.product.BaseCategory1>
     */
    List<BaseCategory1> getCategory1();

    /**
     * 通过一级分类id获取所有二级分类
     * @Author WangYongShuai
     * @Date 14:26 2020/11/28
     * @param category1Id
     * @throws
     * @return java.util.List<com.atguigu.gmall.model.entity.product.BaseCategory2>
     */
    List<BaseCategory2> getCategory2(Long category1Id);

    /**
     * 通过二级分类id获取所有三级分类
     * @Author WangYongShuai
     * @Date 14:27 2020/11/28
     * @param category2Id
     * @throws
     * @return java.util.List<com.atguigu.gmall.model.entity.product.BaseCategory2>
     */
    List<BaseCategory3> getCategory3(Long category2Id);

    /**
     * 类目导航
     * @Author WangYongShuai
     * @Date 20:07 2020/12/2
     * @param category3Id
     * @throws
     * @return com.atguigu.gmall.model.entity.product.BaseCategoryView
     */
    BaseCategoryView getCategoryView(Long category3Id);

    /**
     * 获取首页的分类集合
     * @Author WangYongShuai
     * @Date 23:10 2020/12/7
     * @param
     * @throws
     * @return java.util.List<com.alibaba.fastjson.JSONObject>
     */
    List<JSONObject> getBaseCategoryList();
}

