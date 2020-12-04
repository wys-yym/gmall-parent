package com.atguigu.gmall.product.client;

import com.atguigu.gmall.model.entity.product.BaseCategoryView;
import com.atguigu.gmall.model.entity.product.SkuImage;
import com.atguigu.gmall.model.entity.product.SkuInfo;
import com.atguigu.gmall.model.entity.product.SpuSaleAttr;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.product.client
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/2 13:10
 * @Version: 1.0
 */
@FeignClient("service-product")
public interface ProductFeignClient {
    /**
     * 获取商品价格
     * @Author WangYongShuai
     * @Date 13:17 2020/12/2
     * @param skuId
     * @throws
     * @return java.math.BigDecimal
     */
    @RequestMapping("api/product/getPrice/{skuId}")
    BigDecimal getPrice(@PathVariable Long skuId);

    /**
     * 获取sku对应图片信息
     * @Author WangYongShuai
     * @Date 18:56 2020/12/2
     * @param skuId
     * @throws
     * @return java.util.List<com.atguigu.gmall.model.entity.product.SkuImage>
     */
    @RequestMapping("api/product/getSkuInfo/{skuId}")
    SkuInfo getSkuInfo(@PathVariable Long skuId);

    /**
     * 获取spu的销售属性
     * @Author WangYongShuai
     * @Date 19:45 2020/12/2
     * @param skuId
     * @throws
     * @return java.util.List<com.atguigu.gmall.model.entity.product.SpuSaleAttr>
     */
    @RequestMapping("api/product/getSpuSaleAttrList/{spuId}/{skuId}")
    List<SpuSaleAttr> getSpuSaleAttrList(@PathVariable Long spuId,@PathVariable Long skuId);

    /**
     * 类目导航
     * @Author WangYongShuai
     * @Date 20:02 2020/12/2
     * @param category3Id
     * @throws
     * @return com.atguigu.gmall.model.entity.product.BaseCategoryView
     */
    @RequestMapping("api/product/getCategoryView/{category3Id}")
    BaseCategoryView getCategoryView(@PathVariable Long category3Id);

    /**
     * 通过spuId获取sku与销售属性值的对应关系
     * @Author WangYongShuai
     * @Date 19:12 2020/12/4
     * @param spuId
     * @throws
     * @return java.util.Map<java.lang.String,java.lang.Long>
     */
    @RequestMapping("api/product/getSaleAttrValuesBySpu/{spuId}")
    Map<String, Long> getSaleAttrValuesBySpu(@PathVariable Long spuId);
}
