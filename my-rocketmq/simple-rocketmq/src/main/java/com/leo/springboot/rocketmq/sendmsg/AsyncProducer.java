package com.leo.springboot.rocketmq.sendmsg;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public class AsyncProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("haoke");

        producer.setNamesrvAddr("192.168.203.105:9876");

        producer.start();

        // 发送消息
        String msg = "我的第一个异步发送消息!";
        Message message = new Message("my-topic", msg.getBytes("UTF-8"));
        producer.send(message, new SendCallback() {
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功了!" + sendResult);
                System.out.println("消息id：" + sendResult.getMsgId());
                System.out.println("消息队列：" + sendResult.getMessageQueue());
                System.out.println("消息offset值：" + sendResult.getQueueOffset());
            }

            public void onException(Throwable e) {
                System.out.println("消息发送失败!" + e);
            }
        });

//        producer.shutdown();
    }
}
