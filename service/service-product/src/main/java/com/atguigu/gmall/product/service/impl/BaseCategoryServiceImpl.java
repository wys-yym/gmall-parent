package com.atguigu.gmall.product.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.config.GmallCache;
import com.atguigu.gmall.model.entity.product.BaseCategory1;
import com.atguigu.gmall.model.entity.product.BaseCategory2;
import com.atguigu.gmall.model.entity.product.BaseCategory3;
import com.atguigu.gmall.model.entity.product.BaseCategoryView;
import com.atguigu.gmall.product.mapper.BaseCategory1Mapper;
import com.atguigu.gmall.product.mapper.BaseCategory2Mapper;
import com.atguigu.gmall.product.mapper.BaseCategory3Mapper;
import com.atguigu.gmall.product.mapper.BaseCategoryViewMapper;
import com.atguigu.gmall.product.service.BaseCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.product.service.impl
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/11/28 11:47
 * @Version: 1.0
 */
@Service
public class BaseCategoryServiceImpl implements BaseCategoryService {
    @Autowired
    private BaseCategory1Mapper baseCategory1Mapper;

    @Autowired
    private BaseCategory2Mapper baseCategory2Mapper;

    @Autowired
    private BaseCategory3Mapper baseCategory3Mapper;

    @Autowired
    private BaseCategoryViewMapper baseCategoryViewMapper;

    @Override
    public List<BaseCategory1> getCategory1() {
        List<BaseCategory1> baseCategory1List = baseCategory1Mapper.selectList(null);
        return baseCategory1List;
    }

    @Override
    public List<BaseCategory2> getCategory2(Long category1Id) {
        QueryWrapper<BaseCategory2> wrapper = new QueryWrapper<>();
        wrapper.eq("category1_id",category1Id);
        List<BaseCategory2> baseCategory2List = baseCategory2Mapper.selectList(wrapper);
        return baseCategory2List;
    }

    @Override
    public List<BaseCategory3> getCategory3(Long category2Id) {
        QueryWrapper<BaseCategory3> wrapper = new QueryWrapper<>();
        wrapper.eq("category2_id",category2Id);
        List<BaseCategory3> baseCategory3List = baseCategory3Mapper.selectList(wrapper);
        return baseCategory3List;
    }

    @Override
    public BaseCategoryView getCategoryView(Long category3Id) {
        QueryWrapper<BaseCategoryView> wrapper = new QueryWrapper<>();
        wrapper.eq("category3_id",category3Id);
        BaseCategoryView categoryView = baseCategoryViewMapper.selectOne(wrapper);
        return categoryView;
    }

    @GmallCache
    @Override
    public List<JSONObject> getBaseCategoryList() {
        //查询categoryList
        List<BaseCategoryView> baseCategoryViews = baseCategoryViewMapper.selectList(null);
        //一级分类集合
        List<JSONObject> category1list = new ArrayList<>();
        Map<Long, List<BaseCategoryView>> category1Map = baseCategoryViews.stream().collect(Collectors.groupingBy(BaseCategoryView::getCategory1Id));
        for (Map.Entry<Long, List<BaseCategoryView>> category1Object : category1Map.entrySet()) {
            Long catogory1Id = category1Object.getKey();
            String category1Name = category1Object.getValue().get(0).getCategory1Name();
            JSONObject categor1JsonObject = new JSONObject();
            categor1JsonObject.put("categoryId",catogory1Id);
            categor1JsonObject.put("categoryName",category1Name);

            //二级分类的集合
            List<JSONObject> category2list = new ArrayList<>();
            List<BaseCategoryView> category2Views = category1Object.getValue();
            Map<Long, List<BaseCategoryView>> category2Map = category2Views.stream().collect(Collectors.groupingBy(BaseCategoryView::getCategory2Id));
            for (Map.Entry<Long, List<BaseCategoryView>> category2Object : category2Map.entrySet()) {
                Long catogory2Id = category1Object.getKey();
                String category2Name = category2Object.getValue().get(0).getCategory2Name();
                JSONObject categor2JsonObject = new JSONObject();
                categor2JsonObject.put("categoryId",catogory2Id);
                categor2JsonObject.put("categoryName",category2Name);

                //三级分类的集合
                List<JSONObject> category3list = new ArrayList<>();
                List<BaseCategoryView> category3Views = category2Object.getValue();
                Map<Long, List<BaseCategoryView>> category3Map = category3Views.stream().collect(Collectors.groupingBy(BaseCategoryView::getCategory3Id));
                for (Map.Entry<Long, List<BaseCategoryView>> category3Object : category3Map.entrySet()) {
                    Long catogory3Id = category3Object.getKey();
                    String category3Name = category3Object.getValue().get(0).getCategory3Name();
                    JSONObject categor3JsonObject = new JSONObject();
                    categor3JsonObject.put("categoryId",catogory3Id);
                    categor3JsonObject.put("categoryName",category3Name);
                    category3list.add(categor3JsonObject);
                }
                categor2JsonObject.put("categoryChild",category3list);
                category2list.add(categor2JsonObject);
            }
            categor1JsonObject.put("categoryChild",category2list);
            category1list.add(categor1JsonObject);
        }
        return category1list;
    }
}
