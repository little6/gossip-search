package com.itheima.page.service;

/**
 * @author huyy
 * @Title: SearchPageService
 * @ProjectName gossip-parent
 * @Description: 根据热搜关键词,建立redis缓存
 * @date 2018/11/1820:51
 */
public interface SearchPageService {

    /**
     * 根据热搜关键词生成redis缓存数据
     * @param keyword
     */
    public boolean genSearchHtml(String keyword);
}
