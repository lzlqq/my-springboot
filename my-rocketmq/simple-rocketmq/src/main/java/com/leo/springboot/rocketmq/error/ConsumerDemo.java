package com.leo.springboot.rocketmq.error;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ConsumerDemo {

	public static void main(String[] args) throws Exception {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("HAOKE_IM");
		consumer.setNamesrvAddr("172.16.55.185:9876");
		// 订阅topic，接收此Topic下的所有消息
		consumer.subscribe("my-test-topic", "*");

		consumer.registerMessageListener(new MessageListenerConcurrently() {
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				for (MessageExt msg : msgs) {
					try {
						System.out.println(new String(msg.getBody(), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}

				System.out.println("收到消息->" + msgs);
				if (msgs.get(0).getReconsumeTimes() >= 3) {
					// 重试3次后，不再进行重试
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}

				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
			}
		});
		consumer.start();
	}

}
