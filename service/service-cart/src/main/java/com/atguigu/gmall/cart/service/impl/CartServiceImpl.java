package com.atguigu.gmall.cart.service.impl;

import com.atguigu.gmall.cart.mapper.CartInfoMapper;
import com.atguigu.gmall.cart.service.CartService;
import com.atguigu.gmall.constant.RedisConst;
import com.atguigu.gmall.model.entity.cart.CartInfo;
import com.atguigu.gmall.model.entity.product.SkuInfo;
import com.atguigu.gmall.product.client.ProductFeignClient;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.cart.service.impl
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/14 14:47
 * @Version: 1.0
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartInfoMapper cartInfoMapper;

    @Autowired
    ProductFeignClient productFeignClient;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void addCart(CartInfo cartInfo) {
        SkuInfo skuInfo = productFeignClient.getSkuInfo(cartInfo.getSkuId());
        cartInfo.setImgUrl(skuInfo.getSkuDefaultImg());
        cartInfo.setIsChecked(cartInfo.getIsChecked());
        cartInfo.setUserId(cartInfo.getUserId());
        cartInfo.setCartPrice(skuInfo.getPrice().multiply(new BigDecimal(cartInfo.getSkuNum())));
        cartInfo.setSkuName(skuInfo.getSkuName());
        cartInfoMapper.insert(cartInfo);
        //同步缓存
        redisTemplate.opsForHash().put(RedisConst.USER_KEY_PREFIX + cartInfo.getUserId() + RedisConst.USER_CART_KEY_SUFFIX,
                cartInfo.getSkuId() + "", cartInfo);
    }

    @Override
    public List<CartInfo> cartList(CartInfo cartInfo) {
        //从缓存中获取
        List<CartInfo> cartInfoList = (List<CartInfo>) redisTemplate.opsForHash().values(RedisConst.USER_KEY_PREFIX + cartInfo.getUserId() +
                RedisConst.USER_CART_KEY_SUFFIX);
        HashMap<String, CartInfo> map = new HashMap<>();
        if (null == cartInfoList && cartInfoList.size() < 0) {
            QueryWrapper<CartInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", cartInfo.getUserId());
            cartInfoList = cartInfoMapper.selectList(wrapper);
            if (null != cartInfoList && cartInfoList.size() > 0) {
                for (CartInfo info : cartInfoList) {
                    map.put(info.getSkuId() + "", info);
                }
                //同步缓存
                redisTemplate.opsForHash().putAll(RedisConst.USER_KEY_PREFIX + cartInfo.getUserId() +
                        RedisConst.USER_CART_KEY_SUFFIX, map);
            }
        }
        if (null != cartInfoList && cartInfoList.size() > 0) {
            for (CartInfo info : cartInfoList) {
                SkuInfo skuInfo = productFeignClient.getSkuInfo(info.getSkuId());
                info.setSkuPrice(skuInfo.getPrice());
            }
        }

        return cartInfoList;
    }
}
