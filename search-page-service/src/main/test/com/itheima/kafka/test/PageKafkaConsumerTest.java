package com.itheima.kafka.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @author huyy
 * @Title: PageKafkaConsumerTest
 * @ProjectName gossip-parent
 * @Description: TODO
 * @date 2018/11/1921:01
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
