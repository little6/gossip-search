package com.itheima.page.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.itheima.gossip.pojo.PageBean;
import com.itheima.gossip.pojo.ResultBean;
import com.itheima.page.service.SearchPageService;
import com.itheima.page.utils.JedisUtils;
import com.itheima.search.service.SearchService;
import redis.clients.jedis.Jedis;

/**
 * @author huyy
 * @Title: SearchPageServiceImpl
 * @ProjectName gossip-parent
 * @Description: 根据热搜关键词,创建redis缓存数据
 * @date 2018/11/1820:57
 */
@Service
public class SearchPageServiceImpl implements SearchPageService {

    @Reference
    private SearchService searchService;

    /**
     * 根据热搜关键词生成redis缓存数据
     *
     * @param keyword
     */
    @Override
    public boolean genSearchHtml(String keyword) {
        try {
            //1. 根据关键词查找搜索结果
            ResultBean resultBean = new ResultBean();
            //搜索关键字
            resultBean.setKeywords(keyword);
            //分页数据
            PageBean pageBean = new PageBean();
            pageBean.setPageSize(5);
            pageBean.setPage(1);
            resultBean.setPageBean(pageBean);
            PageBean page = searchService.findPageToKeyWords(resultBean);
            //默认取前5页结果数据
            int pageCount = 5;
            if(page.getPageNumber() < 5){
                //小于5页,有多少页取多少页
                pageCount = page.getPageNumber();
            }
            for (int i = 1; i <= pageCount; i++) {
                resultBean.getPageBean().setPage(i);
                PageBean pageToKeyWords = searchService.findPageToKeyWords(resultBean);
                resultBean.setPageBean(pageToKeyWords);
                //将数据缓存到redis中
                Jedis jedis = JedisUtils.getJedis();
                jedis.set(keyword+":"+i, JSON.toJSONString(resultBean));
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
