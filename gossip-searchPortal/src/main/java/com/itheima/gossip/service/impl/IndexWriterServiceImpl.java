package com.itheima.gossip.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.gossip.mapper.NewsMapper;
import com.itheima.gossip.pojo.News;
import com.itheima.gossip.service.IndexWriterService;
import com.itheima.search.service.IndexWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

/**
 * @author zjl
 * @create 2018-10-05 22:05
 **/
@Service
public class IndexWriterServiceImpl implements IndexWriterService {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private IndexWriter indexWriter;

    @Override
    public void indexWriter() throws Exception {
        // 需要从数据库查询到最新数据, 添加到solr中
        /**
         * 在添加的时候还需要注意以下几个方面:
         *      1. 在获取最新的数据的时候, 如果数据比较多, 如果去全部查询出来, 会导致MySQL查询效率降低, 服务器内存不够
         *      2. 每次只需要获取新增的数据, 而不需要获取所有的信息, 如何获取增量数据
         *
         * 解决方案:
         *      1. 使用 limit进行分页查询, 直至将最后一条数据全部获取结束
         *      2. 采用redis, 将每次添加完的最后一条数据的id,存储到redis中,每次添加数据, 从redis中获取上一次的id值, 向下获取即可
         * 最终查询数据库的SQL:
         *      select * from news where id < ? limit 0 , 100;
         */
        Jedis jedis = jedisPool.getResource();
        jedis.auth("redis");
        String idStr = jedis.get("bigData:gossip:incrIdVal");
        if(idStr == null){
            idStr = "0";
        }

        Integer id = Integer.valueOf(idStr);
        while (true) {
            System.out.println(id+"========");
            List<News> beanList = newsMapper.queryAndIdGtAndPage(id);
            System.out.println(beanList.size());

            if (beanList != null && beanList.size() > 0) {
                //写入索引
                indexWriter.saveBeans(beanList);


                if (beanList.size() < 100) {
                    // 此时说明数据全部获取完毕, 只需要将其中最大的id值存储到redis中即可
                    //获取最大的id值的方案: 1. 进行遍历, 寻找最大的id  2. 重新查询数据库, 根据这个条件, 获取最大的那个id值即可

                    Integer idMax = newsMapper.queryAndIdMax(id);
                    //将最大的id值, 再次保存到redis中
                    jedis.set("bigData:gossip:incrIdVal" , idMax+"");

                    break;

                }
                
                //此时应该还有下一页的数据, 接的获取即可
                id  = newsMapper.queryAndIdMax(id);
                System.out.println(id);
            }else {
                //此时说明没有任何的增量数据, 直接退出即可
                System.out.println("没有任何的增量数据");
                break;
            }

        }
        jedis.close();

    }
}
