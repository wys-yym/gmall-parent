package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.entity.product.BaseTrademark;
import com.atguigu.gmall.product.mapper.BaseTrademarkMapper;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.product.service.impl
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/11/30 12:56
 * @Version: 1.0
 */
@Service
public class BaseTrademarkServiceImpl implements BaseTrademarkService {
    @Autowired
    private BaseTrademarkMapper baseTrademarkMapper;

    @Override
    public List<BaseTrademark> getTrademarkList() {
        return baseTrademarkMapper.selectList(null);
    }

    @Override
    public void baseTrademark(Page<BaseTrademark> page) {
        baseTrademarkMapper.selectPage(page,null);
    }
}
