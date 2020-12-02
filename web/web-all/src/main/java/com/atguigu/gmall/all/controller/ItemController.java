package com.atguigu.gmall.all.controller;

import com.atguigu.gmall.item.client.ItemFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.all.controller
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/2 11:36
 * @Version: 1.0
 */
@Controller
public class ItemController {
    @Autowired
    ItemFeignClient itemFeignClient;

    @RequestMapping("{skuId}.html")
    public String item(@PathVariable Long skuId, Model model) {
        Map<String,Object> map = itemFeignClient.getItem(skuId);
        model.addAllAttributes(map);
        return "item/index";
    }
}
