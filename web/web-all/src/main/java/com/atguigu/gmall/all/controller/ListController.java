package com.atguigu.gmall.all.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.list.client.ListFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.all.controller
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/7 22:21
 * @Version: 1.0
 */
@Controller
public class ListController {
   @Autowired
   ListFeignClient listFeignClient;

    @RequestMapping("/")
    public String index(Model model) {
        List<JSONObject> list = listFeignClient.getBaseCategoryList();
        model.addAttribute("list",list);
        return "index/index";
    }

}
