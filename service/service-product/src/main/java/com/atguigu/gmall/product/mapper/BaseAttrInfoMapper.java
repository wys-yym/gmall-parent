package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.entity.product.BaseAttrInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.product.mapper
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/11/28 21:27
 * @Version: 1.0
 */
@Mapper
public interface BaseAttrInfoMapper extends BaseMapper<BaseAttrInfo> {
    /**
     * 通过分类级别,以及分类Id查询商品属性信息
     * @Author WangYongShuai
     * @Date 0:22 2020/11/29
     * @param i
     * @param category3Id
     * @throws
     * @return java.util.List<com.atguigu.gmall.model.entity.product.BaseAttrInfo>
     */
    List<BaseAttrInfo> selectAttrInfoList(@Param("categoryLevel") int i,@Param("categoryId") String category3Id);
}
