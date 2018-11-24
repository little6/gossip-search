package com.itheima.gossip.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.page.service.SearchPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author huyy
 * @Title: KeywordsCacheController
 * @ProjectName gossip-parent
 * @Description: 热词缓存controller
 * @date 2018/11/1918:07
 */
@Controller
public class KeywordsCacheController {

    @Reference
    private SearchPageService searchPageService;

    /**
     * 根据关键词生成对应的缓存数据
     * @param keywords : 关键词
     * @return
     */
    @RequestMapping("/genKeywordsCache")
    @ResponseBody
    public String genKeywordsCache(String keywords){
        try {
            //处理中文乱码问题
            keywords = new String(keywords.getBytes("iso8859-1"),"utf-8");
            searchPageService.genSearchHtml(keywords);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
