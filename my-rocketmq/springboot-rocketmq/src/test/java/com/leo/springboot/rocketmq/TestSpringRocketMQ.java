package com.leo.springboot.rocketmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.leo.springboot.rocketmq.normal.SpringProducer;
import com.leo.springboot.rocketmq.transaction.SpringTransactionProducer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSpringRocketMQ {

    @Autowired
    private SpringProducer springProducer;

    @Autowired
    private SpringTransactionProducer springTransactionProducer;

    @Test
    public void testSendMsg(){
        String msg = "我的第2个SpringRocketMQ消息!";
        this.springProducer.sendMsg("spring-my-topic", msg);
        System.out.println("发送成功");
    }

    @Test
    public void testSendMsg2(){
        this.springTransactionProducer.sendMsg("spring-tx-my-topic", "第5个Spring事务消息");

        try {
            Thread.sleep(9999999L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
