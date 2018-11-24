package com.itheima.search.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.gossip.pojo.News;
import com.itheima.gossip.pojo.PageBean;
import com.itheima.gossip.pojo.ResultBean;
import com.itheima.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * solr 的搜索实现
 *
 * @author zjl
 * @create 2018-10-07 10:48
 **/
@Service
public class SolrSearchService implements SearchService {

    @Autowired
    private SolrServer solrServer;


    @Override
    public List<News> findToKeyWords(String keywords) throws Exception {

        //1. 封装查询条件
        // text是一个复制域, 查询这个域相当于一并查找了content title source editor
        SolrQuery solrQuery = new SolrQuery("text:" + keywords);

        //1.1  默认按照时间排序
        solrQuery.setSort("time", SolrQuery.ORDER.desc);

        //1.2 封装高亮相关设置
        solrQuery.setHighlight(true); //开启高亮
        solrQuery.setHighlightSimplePre("<font color = 'red'>"); //高亮的前缀
        solrQuery.setHighlightSimplePost("</font>"); // 高亮的后缀
        // 高亮字段
        solrQuery.addHighlightField("title");
        solrQuery.addHighlightField("content");
        solrQuery.addHighlightField("source");
        solrQuery.addHighlightField("editor");

        //2. 调用查询
        QueryResponse response = solrServer.query(solrQuery);

        List<News> newsList = response.getBeans(News.class);
        //3. 获取高亮内容
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        //4. 将高亮的内容填充的结果集中
        for (News news : newsList) {
            String id = news.getId();
            Map<String, List<String>> map = highlighting.get(id);
            for (String filed : map.keySet()) {
                if ("title".equals(filed) && map.get(filed).size() > 0) {
                    news.setTitle(map.get(filed).get(0));
                }
                if ("content".equals(filed) && map.get(filed).size() > 0) {
                    news.setContent(map.get(filed).get(0));
                }
                if ("source".equals(filed) && map.get(filed).size() > 0) {
                    news.setSource(map.get(filed).get(0));
                }
                if ("editor".equals(filed) && map.get(filed).size() > 0) {
                    news.setEditor(map.get(filed).get(0));
                }

            }
        }


        return newsList;
    }

    @Override
    public PageBean findPageToKeyWords(ResultBean resultBean) throws Exception {

        //1. 封装查询条件
        // text是一个复制域, 查询这个域相当于一并查找了content title source editor
        SolrQuery solrQuery = new SolrQuery("text:" + resultBean.getKeywords());

        //1.1  默认按照时间排序
        solrQuery.setSort("time", SolrQuery.ORDER.desc);

        //1.2 封装分页参数
        solrQuery.setStart((2 * resultBean.getPageBean().getPage()) - 2);
        solrQuery.setRows(resultBean.getPageBean().getPageSize());

        //1.3 封装高亮相关设置
        solrQuery.setHighlight(true); //开启高亮
        solrQuery.setHighlightSimplePre("<font color = 'red'>"); //高亮的前缀
        solrQuery.setHighlightSimplePost("</font>"); // 高亮的后缀
        // 高亮字段
        solrQuery.addHighlightField("title");
        solrQuery.addHighlightField("content");
        solrQuery.addHighlightField("source");
        solrQuery.addHighlightField("editor");


        //1.4 添加搜索工具相关的过滤条件参数
        //1.4.1: 日期范围, 如果使用solr完成日期范围查询, 其solr对于日期是尤其固定的格式: yyyy-MM-dd'T'HH:mm:ss'Z'
        if (!StringUtils.isBlank(resultBean.getDateStart()) && !StringUtils.isBlank(resultBean.getDateEnd())) {
            // 故需要进行UTC日期的转换
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
            Date dateStartObj = simpleDateFormat.parse(resultBean.getDateStart());
            // UTC 是国际标准时间, 和北京时间相差8小时, 故需要进行处理
            long time = dateStartObj.getTime() - (1000*60*60*8);
            dateStartObj.setTime(time);
            Date dateEndObj = simpleDateFormat.parse(resultBean.getDateEnd());
            // UTC 是国际标准时间, 和北京时间相差8小时, 故需要进行处理
            time = dateEndObj.getTime() - (1000*60*60*8);
            dateEndObj.setTime(time);

            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            String dateStart = simpleDateFormat.format(dateStartObj);
            String dateEnd = simpleDateFormat.format(dateEndObj);

            System.out.println(dateStart + dateEnd);
            solrQuery.addFilterQuery("time:[" + dateStart + " TO " + dateEnd + "]");
        }

        //1.4.2: 来源
        if (!StringUtils.isBlank(resultBean.getSource())) {
            solrQuery.addFilterQuery("source:" + resultBean.getSource());
        }

        //1.4.3: 编辑者
        if (!StringUtils.isBlank(resultBean.getEditor())) {
            solrQuery.addFilterQuery("editor:" + resultBean.getEditor());
        }


        //2. 调用查询
        QueryResponse response = solrServer.query(solrQuery);

        List<News> newsList = response.getBeans(News.class);

        //3. 获取高亮内容
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        //4. 将高亮的内容填充的结果集中
        for (News news : newsList) {
            String id = news.getId();
            Map<String, List<String>> map = highlighting.get(id);
            for (String filed : map.keySet()) {
                if ("title".equals(filed) && map.get(filed).size() > 0) {
                    news.setTitle(map.get(filed).get(0));
                }
                if ("content".equals(filed) && map.get(filed).size() > 0) {
                    news.setContent(map.get(filed).get(0));
                }
                if ("source".equals(filed) && map.get(filed).size() > 0) {
                    news.setSource(map.get(filed).get(0));
                }
                if ("editor".equals(filed) && map.get(filed).size() > 0) {
                    news.setEditor(map.get(filed).get(0));
                }

            }
        }


        //3. 封装pageBean 返回即可
        PageBean pageBean = resultBean.getPageBean();

        pageBean.setNewsList(newsList);
        Long pageCount = response.getResults().getNumFound();
        pageBean.setPageCount(pageCount.intValue());
        //总页数
        Double pageNumber = Math.ceil((double) pageBean.getPageCount() / pageBean.getPageSize());
        pageBean.setPageNumber(pageNumber.intValue());

        return pageBean;
    }
}
