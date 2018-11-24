package com.itheima.search.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itheima.gossip.pojo.News;
import com.itheima.search.service.IndexWriter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author huyy
 * @Title: SpiderKafkaConsumer
 * @ProjectName gossip-parent
 * @Description: 爬虫数据的消费者
 * @date 2018/11/1819:28
 */
@Component
public class SpiderKafkaConsumer implements MessageListener<Integer,String> {

//    private static Gson gson = new Gson();
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    @Autowired
    private IndexWriter indexWriter;

    @Override
    public void onMessage(ConsumerRecord<Integer, String> record) {
        try {
            //1. 获取kafka的新闻数据
            System.out.println("获取kafka中的爬虫数据.......");
            String value = record.value();
            System.out.println("获取topic中的数据进行消费，内容：" + value);

            //进行转换
            News news = gson.fromJson(value, News.class);

            //设置唯一的id
            news.setId(UUID.randomUUID().toString());

            //2. 调用索引创建服务,创建新闻索引
            ArrayList<News> list = new ArrayList<>(1);
            list.add(news);
            indexWriter.saveBeans(list);
            System.out.println("调用搜索服务进行新闻数据的索引服务........");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
