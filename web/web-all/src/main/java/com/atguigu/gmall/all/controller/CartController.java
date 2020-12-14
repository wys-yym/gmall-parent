package com.atguigu.gmall.all.controller;

import com.atguigu.gmall.cart.client.CartFeignClient;
import com.atguigu.gmall.model.entity.cart.CartInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.all.controller
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/14 13:57
 * @Version: 1.0
 */
@Controller
public class CartController {
    @Autowired
    CartFeignClient cartFeignClient;

    @RequestMapping("addCart.html")
    public String addCart(CartInfo cartInfo) {
        cartFeignClient.addCart(cartInfo);
        return "redirect:/cart/addCart.html?skuNum="+cartInfo.getSkuNum();
    }

    @RequestMapping("cart/cart.html")
    public String index() {
        return "cart/index";
    }
}
