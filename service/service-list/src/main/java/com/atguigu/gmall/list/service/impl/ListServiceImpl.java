package com.atguigu.gmall.list.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.list.repository.GoodsElasticsearchRepository;
import com.atguigu.gmall.list.service.ListService;
import com.atguigu.gmall.model.entity.list.*;
import com.atguigu.gmall.model.entity.product.BaseCategoryView;
import com.atguigu.gmall.model.entity.product.BaseTrademark;
import com.atguigu.gmall.model.entity.product.SkuInfo;
import com.atguigu.gmall.product.client.ProductFeignClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.support.ValueType;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.list.service.impl
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/7 23:05
 * @Version: 1.0
 */
@Service
public class ListServiceImpl implements ListService {
    @Autowired
    ProductFeignClient productFeignClient;

    @Autowired
    GoodsElasticsearchRepository goodsElasticsearchRepository;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    public List<JSONObject> getBaseCategoryList() {
        List<JSONObject> list = productFeignClient.getBaseCategoryList();
        return list;
    }

    @Override
    public void onSale(Long skuId) {
        Goods goods = new Goods();
        //获取sku信息
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        //获取sku对应商标
        BaseTrademark baseTrademark = productFeignClient.getTradeMarkByTmId(skuInfo.getTmId());
        //获取获取搜索属性
        List<SearchAttr> searchAttrList = productFeignClient.getSearchAttrListBySkuId(skuId);
        //获取分类信息
        BaseCategoryView categoryView = productFeignClient.getCategoryView(skuInfo.getCategory3Id());
        goods.setId(skuId);
        goods.setHotScore(0L);
        goods.setCreateTime(new Date());
        goods.setTitle(skuInfo.getSkuName());
        goods.setAttrs(searchAttrList);
        goods.setPrice(skuInfo.getPrice().doubleValue());
        goods.setDefaultImg(skuInfo.getSkuDefaultImg());
        goods.setCategory1Id(categoryView.getCategory1Id());
        goods.setCategory1Name(categoryView.getCategory1Name());
        goods.setCategory2Id(categoryView.getCategory2Id());
        goods.setCategory2Name(categoryView.getCategory2Name());
        goods.setCategory3Id(skuInfo.getCategory3Id());
        goods.setCategory3Name(categoryView.getCategory3Name());
        goods.setTmId(baseTrademark.getId());
        goods.setTmLogoUrl(baseTrademark.getLogoUrl());
        goods.setTmName(baseTrademark.getTmName());
        goodsElasticsearchRepository.save(goods);
    }

    @Override
    public void cancelSale(Long skuId) {
        goodsElasticsearchRepository.deleteById(skuId);
    }

    @Override
    public void createGoodsIndex() {
        elasticsearchRestTemplate.createIndex(Goods.class);
        elasticsearchRestTemplate.putMapping(Goods.class);
    }

    @Override
    public SearchResponseVo list(SearchParam searchParam) {
        SearchResponse searchResult = null;
        //解析请求
        SearchRequest searchRequest = getSearchRequest(searchParam);
        try {
            searchResult = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //解析返回的结果
        SearchResponseVo searchResponseVo = parseResponse(searchResult);
        return searchResponseVo;
    }

    /**
     * 解析响应结果
     * @param searchResult
     * @return
     */
    private SearchResponseVo parseResponse(SearchResponse searchResult) {
        SearchResponseVo searchResponseVo = new SearchResponseVo();
        SearchHits hits = searchResult.getHits();
        SearchHit[] resultHit = hits.getHits();
        List<Goods> goodList = new ArrayList<>();
        //商品集合
        if (null != resultHit && resultHit.length > 0) {
            for (SearchHit documentFields : resultHit) {
                Goods goods = JSON.parseObject(documentFields.getSourceAsString(), Goods.class);
                goodList.add(goods);
            }
        }
        searchResponseVo.setGoodsList(goodList);
        ParsedLongTerms tmIdAgg = searchResult.getAggregations().get("tmIdAgg");
        List<SearchResponseTmVo> searchResponseTmVoList = tmIdAgg.getBuckets().stream().map(tmIdAggBucket -> {
            SearchResponseTmVo searchResponseTmVo = new SearchResponseTmVo();
            //id
            long tmId = tmIdAggBucket.getKeyAsNumber().longValue();
            //name
            ParsedStringTerms tmNameAgg = tmIdAggBucket.getAggregations().get("tmNameAgg");
            String tmName = tmNameAgg.getBuckets().get(0).getKeyAsString();
            //logUrl
            ParsedStringTerms tmLogoUrlAgg = tmIdAggBucket.getAggregations().get("tmLogoUrlAgg");
            String tmLogUrl = tmLogoUrlAgg.getBuckets().get(0).getKeyAsString();
            searchResponseTmVo.setTmId(tmId);
            searchResponseTmVo.setTmName(tmName);
            searchResponseTmVo.setTmLogoUrl(tmLogUrl);
            return searchResponseTmVo;
        }).collect(Collectors.toList());
        searchResponseVo.setTrademarkList(searchResponseTmVoList);

        return searchResponseVo;
    }

    /**
     * 解析商标聚合
     * @param searchResult
     * @return
     */
    private List<SearchResponseTmVo> getSearchResponseTmVos(SearchResponse searchResult) {
        //商标集合
        ParsedLongTerms tmIdAgg = searchResult.getAggregations().get("tmIdAgg");
        List<SearchResponseTmVo> searchResponseTmVoList = new ArrayList<>();
        List<? extends Terms.Bucket> tmIdAggBuckets = tmIdAgg.getBuckets();
        for (Terms.Bucket bucket : tmIdAggBuckets) {
            SearchResponseTmVo searchResponseTmVo = new SearchResponseTmVo();
            //id
            long tmId = bucket.getKeyAsNumber().longValue();
            searchResponseTmVo.setTmId(tmId);
            //name
            ParsedStringTerms tmNameAgg = bucket.getAggregations().get("tmNameAgg");
            List<? extends Terms.Bucket> tmNameAggBuckets = tmNameAgg.getBuckets();
            String tmName = tmNameAggBuckets.get(0).getKeyAsString();
            searchResponseTmVo.setTmName(tmName);
            //logUrl
            ParsedStringTerms tmUrlAgg = bucket.getAggregations().get("tmLogoUrlAgg");
            List<? extends Terms.Bucket> tmUrlAggBuckets = tmUrlAgg.getBuckets();
            String tmUrl = tmUrlAggBuckets.get(0).getKeyAsString();
            searchResponseTmVo.setTmLogoUrl(tmUrl);
            searchResponseTmVoList.add(searchResponseTmVo);
        }
        return searchResponseTmVoList;
    }

    /**
     * 解析请求
     * @param searchParam
     * @return
     */
    private SearchRequest getSearchRequest(SearchParam searchParam) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("goods");
        searchRequest.types("info");
        Long category3Id = searchParam.getCategory3Id();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //三级分类
        if (null != category3Id && category3Id > 0) {
            searchSourceBuilder.query(QueryBuilders.termQuery("category3Id", category3Id));
        }
        //商标聚合
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("tmIdAgg").field("tmId")
                .subAggregation(AggregationBuilders.terms("tmNameAgg").field("tmName"))
                .subAggregation(AggregationBuilders.terms("tmLogoUrlAgg").field("tmLogoUrl"));
        searchSourceBuilder.aggregation(aggregationBuilder);
        System.out.println(searchSourceBuilder.toString());
        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }


}
