package com.itheima.search.service;

import com.itheima.gossip.pojo.News;
import com.itheima.gossip.pojo.PageBean;
import com.itheima.gossip.pojo.ResultBean;
import org.apache.solr.client.solrj.SolrServerException;

import java.util.List;

/**
 * @author zjl
 * @create 2018-10-07 10:47
 **/
public interface SearchService {
   public List<News> findToKeyWords(String keywords) throws Exception;

    public PageBean findPageToKeyWords(ResultBean resultBean)throws Exception;
}
