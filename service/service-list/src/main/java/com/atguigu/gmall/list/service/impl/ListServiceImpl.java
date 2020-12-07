package com.atguigu.gmall.list.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.list.service.ListService;
import com.atguigu.gmall.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.list.service.impl
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/7 23:05
 * @Version: 1.0
 */
@Service
public class ListServiceImpl implements ListService {
    @Autowired
    ProductFeignClient productFeignClient;

    @Override
    public List<JSONObject> getBaseCategoryList() {
        List<JSONObject> list = productFeignClient.getBaseCategoryList();
        return list;
    }
}
