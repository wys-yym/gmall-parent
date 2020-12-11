package com.atguigu.gmall.list.client;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.model.entity.list.SearchParam;
import com.atguigu.gmall.model.entity.list.SearchResponseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 上架，同步到搜索引擎
     * @Author WangYongShuai
     * @Date 15:31 2020/12/9
     * @param skuId
     * @throws
     * @return void
     */
    @RequestMapping("api/list/onSale/{skuId}")
    void onSale(@PathVariable Long skuId);

    /**
     * 下架，从搜索引擎移除
     * @Author WangYongShuai
     * @Date 15:31 2020/12/9
     * @param skuId
     * @throws
     * @return void
     */
    @RequestMapping("api/list/cancelSale/{skuId}")
    void cancelSale(@PathVariable Long skuId);

    /**
     * 获取搜索请求所响应的结果
     * @Author WangYongShuai
     * @Date 14:20 2020/12/11
     * @param searchParam
     * @throws
     * @return com.atguigu.gmall.model.entity.list.SearchResponseVo
     */
    @RequestMapping("api/list/list")
    SearchResponseVo list(@RequestBody SearchParam searchParam);
}
