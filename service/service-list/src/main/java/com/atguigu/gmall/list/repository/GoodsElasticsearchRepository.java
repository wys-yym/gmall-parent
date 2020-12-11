package com.atguigu.gmall.list.repository;

import com.atguigu.gmall.model.entity.list.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.list.repository
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/10 21:36
 * @Version: 1.0
 */
public interface GoodsElasticsearchRepository extends ElasticsearchRepository<Goods,Long> {
}
