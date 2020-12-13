package com.atguigu.gmall.all.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.list.client.ListFeignClient;
import com.atguigu.gmall.model.entity.list.SearchAttr;
import com.atguigu.gmall.model.entity.list.SearchParam;
import com.atguigu.gmall.model.entity.list.SearchResponseAttrVo;
import com.atguigu.gmall.model.entity.list.SearchResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.all.controller
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/7 22:21
 * @Version: 1.0
 */
@Controller
public class ListController {
    @Autowired
    ListFeignClient listFeignClient;

    @RequestMapping("/")
    public String index(Model model) {
        List<JSONObject> list = listFeignClient.getBaseCategoryList();
        model.addAttribute("list", list);
        return "index/index";
    }

    @RequestMapping({"list.html", "search.html"})
    public String list(Model model, SearchParam searchParam, HttpServletRequest request) {
        SearchResponseVo searchResponseVo = listFeignClient.list(searchParam);
        //获取当前Url
        String requestUri = getRequestUri(searchParam, request);
        if (searchResponseVo != null) {
            model.addAttribute("goodsList", searchResponseVo.getGoodsList());
            model.addAttribute("trademarkList", searchResponseVo.getTrademarkList());
            model.addAttribute("attrsList", searchResponseVo.getAttrsList());
            model.addAttribute("urlParam", requestUri);
        }
        if (null != searchParam.getProps() && searchParam.getProps().length > 0) {
            ArrayList<SearchAttr> searchAttrs = new ArrayList<>();
            String[] props = searchParam.getProps();
            for (String prop : props) {
                String[] split = prop.split(":");
                Long attrId = Long.parseLong(split[0]);
                String attrValue = split[1];
                String attrName = split[2];
                SearchAttr searchAttr = new SearchAttr();
                searchAttr.setAttrId(attrId);
                searchAttr.setAttrName(attrName);
                searchAttr.setAttrValue(attrValue);
                searchAttrs.add(searchAttr);
            }
            model.addAttribute("propsParamList", searchAttrs);
        }
        if (!StringUtils.isEmpty(searchParam.getTrademark())) {
            model.addAttribute("trademarkParam", searchParam.getTrademark().split(":")[1]);
        }
        if (!StringUtils.isEmpty(searchParam.getOrder())) {
            HashMap<String, String> orderMap = new HashMap<>();
            String order = searchParam.getOrder();
            String[] split = order.split(":");
            String orderId = split[0];
            String orderName = split[1];
            orderMap.put("type", orderId);
            orderMap.put("sort", orderName);
            model.addAttribute("orderMap", orderMap);
        }
        return "list/index";
    }

    private String getRequestUri(SearchParam searchParam, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        Long category3Id = searchParam.getCategory3Id();
        String[] props = searchParam.getProps();
        String keyword = searchParam.getKeyword();
        String trademark = searchParam.getTrademark();
        String order = searchParam.getOrder();
        StringBuffer stringBuffer = new StringBuffer(requestURI);
        //三级分类
        if (null != category3Id && category3Id > 0) {
            stringBuffer.append("?category3Id=" + category3Id);
        }
        //关键字
        if (!StringUtils.isEmpty(keyword)) {
            stringBuffer.append("?keyword=" + keyword);
        }
        //属性
        if (null != props && props.length > 0) {
            for (String prop : props) {
                stringBuffer.append("&props=" + prop);
            }
        }
        //商标
        if (!StringUtils.isEmpty(trademark)) {
            stringBuffer.append("&trademark=" + trademark);
        }
        return stringBuffer.toString();
    }
}
