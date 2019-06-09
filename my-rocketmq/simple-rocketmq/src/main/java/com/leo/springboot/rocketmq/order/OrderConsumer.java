package com.leo.springboot.rocketmq.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class OrderConsumer {
	public static void main(String[] args) throws Exception {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("HAOKE_ORDER_CONSUMER");
		consumer.setNamesrvAddr("192.168.203.105:9876");
		consumer.subscribe("haoke_order_topic", "*");
		consumer.registerMessageListener(new MessageListenerOrderly() {
			@Override
			public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
				for (MessageExt msg : msgs) {
					try {
						System.out.println(Thread.currentThread().getName() + " " + msg.getQueueId() + " "
								+ new String(msg.getBody(), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}

				// System.out.println(Thread.currentThread().getName() + " Receive New Messages:
				// " + msgs);
				return ConsumeOrderlyStatus.SUCCESS;
			}
		});
		consumer.start();
	}
}