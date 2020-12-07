package com.atguigu.gmall.list.client;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.list.client
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/7 22:46
 * @Version: 1.0
 */
@FeignClient("service-list")
public interface ListFeignClient {
    /**
     * 获取首页分类集合
     * @Author WangYongShuai
     * @Date 22:53 2020/12/7
     * @param
     * @throws
     * @return java.util.List<com.alibaba.fastjson.JSONObject>
     */
    @RequestMapping("api/list/getBaseCategoryList")
    List<JSONObject> getBaseCategoryList();
}
