package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.entity.list.SearchAttr;
import com.atguigu.gmall.model.entity.product.BaseAttrInfo;
import com.atguigu.gmall.model.entity.product.BaseAttrValue;
import com.atguigu.gmall.model.entity.product.BaseSaleAttr;
import com.atguigu.gmall.product.mapper.BaseAttrInfoMapper;
import com.atguigu.gmall.product.mapper.BaseAttrValueMapper;
import com.atguigu.gmall.product.mapper.BaseSaleAttrMapper;
import com.atguigu.gmall.product.service.BaseAttrService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.product.service.impl
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/11/28 21:21
 * @Version: 1.0
 */
@Service
public class BaseAttrServiceImpl implements BaseAttrService {
    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;

    @Override
    public List<BaseAttrInfo> attrInfoList(String category3Id) {
        List<BaseAttrInfo> baseAttrInfoList = baseAttrInfoMapper.selectAttrInfoList(3,category3Id);
        return baseAttrInfoList;
    }

    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        Long attrInfoId = baseAttrInfo.getId();
        if (null != attrInfoId && attrInfoId > 0) {
            //修改商品属性信息
            baseAttrInfoMapper.updateById(baseAttrInfo);
            QueryWrapper<BaseAttrValue> wrapper = new QueryWrapper<>();
            wrapper.eq("attr_id",attrInfoId);
            baseAttrValueMapper.delete(wrapper);
        } else {
            //添加商品属性信息
            baseAttrInfoMapper.insert(baseAttrInfo);
            Long arrtId = baseAttrInfo.getId();
            attrInfoId = arrtId;
        }
        //添加对应属性值信息
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        for (BaseAttrValue baseAttrValue : attrValueList) {
            baseAttrValue.setAttrId(attrInfoId);
            baseAttrValueMapper.insert(baseAttrValue);
        }
    }

    @Override
    public List<BaseAttrValue> getAttrValueList(Long attrId) {
        QueryWrapper<BaseAttrValue> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_id",attrId);
        List<BaseAttrValue> attrValueList = baseAttrValueMapper.selectList(wrapper);
        return attrValueList;
    }

    @Override
    public List<BaseSaleAttr> baseSaleAttrList() {
        return baseSaleAttrMapper.selectList(null);
    }

    @Override
    public List<SearchAttr> getSearchAttrListBySkuId(Long skuId) {
        List<SearchAttr> searchAttrList = baseAttrInfoMapper.getSearchAttrListBySkuId(skuId);
        return searchAttrList;
    }
}
