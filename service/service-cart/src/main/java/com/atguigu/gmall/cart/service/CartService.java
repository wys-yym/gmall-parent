package com.atguigu.gmall.cart.service;

import com.atguigu.gmall.model.entity.cart.CartInfo;

import java.util.List;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.cart.service
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/14 14:46
 * @Version: 1.0
 */
public interface CartService {
    /**
     *
     * @Author WangYongShuai
     * @Date 14:47 2020/12/14
     * @param
     * @throws
     * @return void
     */
    void addCart(CartInfo cartInfo);

    /**
     * 获取购物车信息
     * @Author WangYongShuai
     * @Date 18:19 2020/12/14
     * @param
     * @throws
     * @return java.util.List<com.atguigu.gmall.model.entity.cart.CartInfo>
     */
    List<CartInfo> cartList(CartInfo cartInfo);
}
