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
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
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

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<JSONObject> getBaseCategoryList() {
        List<JSONObject> list = productFeignClient.getBaseCategoryList();
        return list;
    }

    @Override
    public void hotScore(Long skuId) {
        //将热度值放入缓冲区
        Integer hotScoreRedis = (Integer) redisTemplate.opsForValue().get("hotScore" + skuId);
        if (null == hotScoreRedis) {
            redisTemplate.opsForValue().set("hotScore" + skuId, 1);
        } else {
            hotScoreRedis++;
            redisTemplate.opsForValue().set("hotScore" + skuId, hotScoreRedis);
            if (hotScoreRedis % 10 == 0) {
                Goods goods = goodsElasticsearchRepository.findById(skuId).get();
                if (null != goods) {
                    goods.setHotScore(Long.parseLong(hotScoreRedis + ""));
                }
                goodsElasticsearchRepository.save(goods);
            }
        }
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
     *
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
                //设置高亮属性
                Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
                if (null != highlightFields) {
                    Set<Map.Entry<String, HighlightField>> entries = highlightFields.entrySet();
                    for (Map.Entry<String, HighlightField> entry : entries) {
                        HighlightField highlightField = entry.getValue();
                        Text text = highlightField.getFragments()[0];
                        String hignlightTitle = text.toString();
                        goods.setTitle(hignlightTitle);
                    }
                }
                goodList.add(goods);
            }
        }
        searchResponseVo.setGoodsList(goodList);

        //商标
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

        //属性
        Nested attrsAgg = searchResult.getAggregations().get("attrsAgg");
        ParsedLongTerms attrIdAgg = attrsAgg.getAggregations().get("attrIdAgg");
        List<SearchResponseAttrVo> searchResponseAttrVoList = attrIdAgg.getBuckets().stream().map(attrIdBucket -> {
            SearchResponseAttrVo searchResponseAttrVo = new SearchResponseAttrVo();
            //attrId
            long attrId = attrIdBucket.getKeyAsNumber().longValue();
            //attrName
            ParsedStringTerms attrNameAgg = attrIdBucket.getAggregations().get("attrNameAgg");
            String attrName = attrNameAgg.getBuckets().get(0).getKeyAsString();
            //attrValue
            ParsedStringTerms attrValueAgg = attrIdBucket.getAggregations().get("attrValueAgg");
            List<String> attrValueList = attrValueAgg.getBuckets().stream().map(attrValueBucket -> {
                String attrValue = attrValueBucket.getKeyAsString();
                return attrValue;
            }).collect(Collectors.toList());
            searchResponseAttrVo.setAttrId(attrId);
            searchResponseAttrVo.setAttrName(attrName);
            searchResponseAttrVo.setAttrValueList(attrValueList);
            return searchResponseAttrVo;
        }).collect(Collectors.toList());
        searchResponseVo.setAttrsList(searchResponseAttrVoList);
        return searchResponseVo;
    }

    /**
     * 解析商标聚合
     *
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
     *
     * @param searchParam
     * @return
     */
    private SearchRequest getSearchRequest(SearchParam searchParam) {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchRequest.indices("goods");
        searchRequest.types("info");
        Long category3Id = searchParam.getCategory3Id();
        String[] props = searchParam.getProps();
        String keyword = searchParam.getKeyword();
        String trademark = searchParam.getTrademark();
        String order = searchParam.getOrder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //商标
        if (!StringUtils.isEmpty(trademark)) {
            String[] split = trademark.split(":");
            String tmId = split[0];
            TermQueryBuilder tmIdTermQueryBuilder = new TermQueryBuilder("tmId", tmId);
            boolQueryBuilder.filter(tmIdTermQueryBuilder);
        }

        //属性
        if (null != props && props.length > 0) {
            for (String prop : props) {
                String[] split = prop.split(":");
                Long attrId = Long.parseLong(split[0]);
                String attrValue = split[1];
                String attrName = split[2];
                BoolQueryBuilder nestedBoolQueryBuilder = new BoolQueryBuilder();
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("attrs.attrId", attrId);
                MatchQueryBuilder attrValueMatchQueryBuilder = new MatchQueryBuilder("attrs.attrValue", attrValue);
                MatchQueryBuilder attrNameMatchQueryBuilder = new MatchQueryBuilder("attrs.attrName", attrName);
                nestedBoolQueryBuilder.filter(termQueryBuilder);
                nestedBoolQueryBuilder.must(attrValueMatchQueryBuilder);
                nestedBoolQueryBuilder.must(attrNameMatchQueryBuilder);
                NestedQueryBuilder nestedQueryBuilder = new NestedQueryBuilder("attrs", nestedBoolQueryBuilder, ScoreMode.None);
                boolQueryBuilder.filter(nestedQueryBuilder);
            }
        }
        //关键字
        if (!StringUtils.isEmpty(keyword)) {
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("title", keyword);
            boolQueryBuilder.must(matchQueryBuilder);
        }
        //三级分类
        if (null != category3Id && category3Id > 0) {
            TermQueryBuilder termsQueryBuilder = new TermQueryBuilder("category3Id", category3Id);
            boolQueryBuilder.filter(termsQueryBuilder);
        }
        searchSourceBuilder.query(boolQueryBuilder);
        //页面
        searchSourceBuilder.size(20);
        searchSourceBuilder.from(0);
        //高亮字段
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red;font-weight:bolder'>");
        highlightBuilder.field("title");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
        //排序
        if (!StringUtils.isEmpty(order)) {
            String[] split = order.split(":");
            String orderId = split[0];
            String orderName = split[1];
            String sortName = "hotScore";
            if ("2".equals(orderId)) {
                sortName = "price";
            }
            searchSourceBuilder.sort(sortName, "asc".equals(orderName) ? SortOrder.ASC : SortOrder.DESC);
        }
        //商标聚合
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("tmIdAgg").field("tmId")
                .subAggregation(AggregationBuilders.terms("tmNameAgg").field("tmName"))
                .subAggregation(AggregationBuilders.terms("tmLogoUrlAgg").field("tmLogoUrl"));
        searchSourceBuilder.aggregation(aggregationBuilder);
        //属性聚合
        NestedAggregationBuilder nestedAggregationBuilder = AggregationBuilders.nested("attrsAgg", "attrs").subAggregation(
                AggregationBuilders.terms("attrIdAgg").field("attrs.attrId")
                        .subAggregation(AggregationBuilders.terms("attrNameAgg").field("attrs.attrName"))
                        .subAggregation(AggregationBuilders.terms("attrValueAgg").field("attrs.attrValue"))
        );
        searchSourceBuilder.aggregation(nestedAggregationBuilder);
        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }
}
