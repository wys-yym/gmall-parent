package com.atguigu.gmall.item.controller;

import com.atguigu.gmall.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.item.controller
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/2 13:00
 * @Version: 1.0
 */
@RestController
@RequestMapping("api/item")
public class ItemApiController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("getItem/{skuId}")
    Map<String, Object> getItem(@PathVariable Long skuId) {
        Map<String, Object> map = itemService.getItem(skuId);
        return map;
    }
}
