package com.itheima.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.gossip.pojo.News;
import com.itheima.search.service.IndexWriter;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *
 * solr的 索引写入实现类
 * @author zjl
 * @create 2018-10-05 21:45
 **/
@Service
public class SolrIndexWriter implements IndexWriter {

    @Autowired
    private SolrServer solrServer;


    @Override
    public void saveBeans(List<News> beanList) throws Exception {

        solrServer.addBeans(beanList);

        solrServer.commit();
    }
}
