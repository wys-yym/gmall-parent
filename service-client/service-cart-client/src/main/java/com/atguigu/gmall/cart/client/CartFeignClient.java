package com.atguigu.gmall.cart.client;

import com.atguigu.gmall.model.entity.cart.CartInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.cart.client
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/14 14:39
 * @Version: 1.0
 */
@FeignClient("service-cart")
public interface CartFeignClient {
    /**
     * 添加到购物车
     * @Author WangYongShuai
     * @Date 14:43 2020/12/14
     * @param cartInfo
     * @throws
     * @return void
     */
    @RequestMapping("api/cart/addCart")
    void addCart(@RequestBody CartInfo cartInfo);
}
