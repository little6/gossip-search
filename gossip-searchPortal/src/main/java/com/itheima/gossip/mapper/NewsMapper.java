package com.itheima.gossip.mapper;

import com.itheima.gossip.pojo.News;

import java.util.List;
import java.util.Map;

/**
 * @author zjl
 * @create 2018-10-05 22:13
 **/
public interface NewsMapper {

    // 查询数据
    public List<News> queryAndIdGtAndPage(Integer id);

    // 根据id, 获取查询结果中最大的id的值

    public Integer queryAndIdMax(Integer id);
    // 根据id获取新闻详情页数据
    public String queryAndIdToContent(Integer id);
}
