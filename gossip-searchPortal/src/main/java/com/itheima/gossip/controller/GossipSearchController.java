package com.itheima.gossip.controller;

import com.itheima.gossip.pojo.News;
import com.itheima.gossip.pojo.PageBean;
import com.itheima.gossip.pojo.ResultBean;
import com.itheima.gossip.service.GossipSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索的controller
 *
 * @author zjl
 * @create 2018-10-07 10:23
 **/
@Controller
public class GossipSearchController {
    @Autowired
    private GossipSearchService gossipSearchService;

    @RequestMapping("/s")
    @ResponseBody
    public List<News> findToKeyWords(String keywords) {
        try {
            keywords = new String(keywords.getBytes("ISO-8859-1"), "UTF-8");

            //1. 判断是否接收到数据
            if (keywords == null || keywords == "") {
                return new ArrayList<>(); //返回一个空数组, 表示需要前端跳转首页
            }

            //2. 调用service:
            List<News> newsList = gossipSearchService.findToKeyWords(keywords);

            //3. 将数据返回即可

            return newsList;
        } catch (Exception e) {
            e.printStackTrace();
            //如果出现异常, 直接抛出, 并返回给前端, 跳转到首页, 此处也可以做一些其他错误, 提示相关错误也是OK的
            return new ArrayList<>();
        }
    }

    @RequestMapping("/ps")
    @ResponseBody
    public ResultBean findPageToKeyWords(ResultBean resultBean) {
        try {
            //resultBean.getKeywords() = new String(resultBean.getKeywords().getBytes("ISO-8859-1"),"UTF-8");

            //1. 判断是否接收到数据
            if (resultBean != null && resultBean.getPageBean() != null) {

                if (resultBean.getKeywords() == null || resultBean.getKeywords() == "") {
                    return null; //返回一个空数组, 表示需要前端跳转首页
                }

                if(resultBean.getPageBean().getPage()==null){
                    resultBean.getPageBean().setPage(1);
                }

                if(resultBean.getPageBean().getPageSize()==null){
                    resultBean.getPageBean().setPage(5);
                }

            } else {
                return new ResultBean("参数不正确",false); //返回一个空数组, 表示需要前端跳转首页

            }

            //2. 调用service:
            //List<News> newsList = gossipSearchService.findToKeyWords(keywords);
            PageBean pageBean = gossipSearchService.findPageToKeyWords(resultBean);

            //3. 将数据填充到resultBean中返回
            resultBean.setPageBean(pageBean);
            return resultBean;
        } catch (Exception e) {
            e.printStackTrace();
            //如果出现异常, 直接抛出, 并返回给前端, 跳转到首页, 此处也可以做一些其他错误, 提示相关错误也是OK的
            return new ResultBean("参数不正确",false);
        }
    }

}
