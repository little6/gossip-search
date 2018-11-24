package com.itheima.page.service.impl;

import com.itheima.page.service.SearchPageService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author huyy
 * @Title: KeywordsKafkaConsumer
 * @ProjectName gossip-parent
 * @Description: TODO
 * @date 2018/11/1920:58
 */
@Component
public class KeywordsKafkaConsumer implements MessageListener<Integer,String>{

    @Autowired
    private SearchPageService searchPageService;

    @Override
    public void onMessage(ConsumerRecord<Integer, String> record) {
        //1.获取热搜关键词
        System.out.println("获取kafka中的热门关键词数据.......");
        String value = record.value();
        System.out.println("获取topic中的数据进行消费，内容：" + value);

        //2. 调用热搜词消费服务,创建对应的缓存数据
        searchPageService.genSearchHtml(value);
        System.out.println("调用搜索服务进行新闻数据的索引服务........");
    }
}
