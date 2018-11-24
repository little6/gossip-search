package com.itheima.search.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @author huyy
 * @Title: PageKafkaConsumerTest
 * @ProjectName gossip-parent
 * @Description: kafka消费者的测试类
 * @date 2018/11/1819:31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml",
        "classpath:applicationContext-consumer.xml"})
public class PageKafkaConsumerTest {


    @Test
    public void testPageConsumer(){
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
