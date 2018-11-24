package com.itheima.gossip.service;

import com.itheima.gossip.pojo.News;
import com.itheima.gossip.pojo.PageBean;
import com.itheima.gossip.pojo.ResultBean;

import java.util.List;

/**
 * @author zjl
 * @create 2018-10-07 10:32
 **/
public interface GossipSearchService {
    public List<News> findToKeyWords(String keywords) throws Exception;

    public PageBean findPageToKeyWords(ResultBean resultBean) throws Exception;
}
