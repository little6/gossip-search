package com.itheima.gossip.timing;

import com.itheima.gossip.service.IndexWriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author zjl
 * @create 2018-10-05 23:08
 **/
@Repository
public class IndexWriterTiming {

    @Autowired
    private IndexWriterService indexWriterService;
    // 每隔15分钟执行一次增量数据写入
    @Scheduled(cron = "0 0/1 * * * ?")
    public void  indexWriter() {
       System.out.println(new Date().toLocaleString());
        try {
            indexWriterService.indexWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
