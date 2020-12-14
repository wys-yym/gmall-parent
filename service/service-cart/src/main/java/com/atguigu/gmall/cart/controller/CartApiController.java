package com.atguigu.gmall.cart.controller;

import com.atguigu.gmall.cart.service.CartService;
import com.atguigu.gmall.model.entity.cart.CartInfo;
import com.atguigu.gmall.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.cart.controller
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/14 14:36
 * @Version: 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("api/cart")
public class CartApiController {
    @Autowired
    CartService cartService;

    @RequestMapping("addCart")
    void addCart(@RequestBody CartInfo cartInfo) {
        //从oss单点登录获取
        String userId = "1";
        cartInfo.setUserId(userId);
        cartService.addCart(cartInfo);
    }

    @RequestMapping("cartList")
    public Result cartList() {
        //从oss单点登录获取
        String userId = "1";
        CartInfo cartInfo = new CartInfo();
        cartInfo.setUserId(userId);
        List<CartInfo> cartInfos = cartService.cartList(cartInfo);
        return Result.ok(cartInfos);
    }
}
