package com.atguigu.gmall.list.service;

import com.alibaba.fastjson.JSONObject;

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
}
