package com.itheima.search.service;

import com.itheima.gossip.pojo.News;

import java.util.List;

/**
 * @author zjl
 * @create 2018-10-05 21:43
 **/
public interface IndexWriter {

    public void saveBeans(List<News> beanList) throws Exception;
}
