package com.itheima.gossip.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.gossip.pojo.News;
import com.itheima.gossip.pojo.PageBean;
import com.itheima.gossip.pojo.ResultBean;
import com.itheima.gossip.service.GossipSearchService;
import com.itheima.search.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zjl
 * @create 2018-10-07 10:33
 **/
@Service
public class GossipSearchServiceImpl implements GossipSearchService{
    @Reference
    private SearchService solrSearchService;


    @Override
    public List<News> findToKeyWords(String keywords) throws Exception {
        //1. 调用solr服务, 获取新闻数据, 此时的数据中并没有新闻的详情页
        List<News> newsList = solrSearchService.findToKeyWords(keywords);
        //2. 封装新闻详情页
        for (News news : newsList) {

            //2.1 页面只展示详情页100以内的内容
           /* if(news.getContent().length()>100){
                news.setContent(news.getContent().substring(0,99)+" ...");
            }*/

        }

        return newsList;
    }

    // 分页获取数据
    @Override
    public PageBean findPageToKeyWords(ResultBean resultBean) throws Exception {

        //1. 调用搜索服务, 分页获取数据: 服务只封装了每页的数据和总条数
        PageBean pageBean =  solrSearchService.findPageToKeyWords(resultBean);

        //2. 处理详情页数据
        for (News news : pageBean.getNewsList()) {

            //2.1 页面只展示详情页100以内的内容
           /* if(news.getContent().length()>100){
                news.setContent(news.getContent().substring(0,99)+" ...");
            }*/

        }

        //3. 封装分页数据:还需要封装总页数(计算)
        Double pageNumber = Math.ceil((double) pageBean.getPageCount() / pageBean.getPageSize());
        pageBean.setPageNumber(pageNumber.intValue());
        return pageBean;
    }
}
