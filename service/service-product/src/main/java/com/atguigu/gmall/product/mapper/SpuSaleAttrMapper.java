package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.entity.product.SpuSaleAttr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.product.mapper
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/11/30 13:17
 * @Version: 1.0
 */
@Mapper
public interface SpuSaleAttrMapper extends BaseMapper<SpuSaleAttr> {
    /**
     * 前端获取spu属性
     * @Author WangYongShuai
     * @Date 15:35 2020/12/4
     * @param spuId
     * @param skuId
     * @throws
     * @return java.util.List<com.atguigu.gmall.model.entity.product.SpuSaleAttr>
     */
    List<SpuSaleAttr> selectSpuSaleAttrList(@Param("spuId") Long spuId,@Param("skuId") Long skuId);

    /**
     * 通过spuId获取sku与销售属性值的对应关系
     * @Author WangYongShuai
     * @Date 19:19 2020/12/4
     * @param spuId
     * @throws
     * @return java.util.List<java.util.Map>
     */
    List<Map> getSaleAttrValuesBySpu(Long spuId);
}
