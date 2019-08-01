package com.leo.springboot.rocketmq.order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class OrderProducer {

	public static void main(String[] args) throws Exception {
		DefaultMQProducer producer = new DefaultMQProducer("HAOKE_ORDER_PRODUCER");
		producer.setNamesrvAddr("192.168.203.105:9876");
		producer.start();
		for (int i = 0; i < 100; i++) {
			int orderId = i % 10; // 模拟生成订单id
			String msgStr = "order --> " + i + ", id = " + orderId;
			Message message = new Message("haoke_order_topic", "ORDER_MSG",
					msgStr.getBytes(RemotingHelper.DEFAULT_CHARSET));
			SendResult sendResult = producer.send(message, (mqs, msg, arg) -> {
				Integer id = (Integer) arg;
				// 1.mqs是topic下的分区，orderId取模分区数，就会把同一个orderId发送到同一个分区上
				// 2.顺序发送时，才有的是同步发送，保证同一个分区消息有序
				int index = id % mqs.size();
				return mqs.get(index);
			}, orderId);
			System.out.println(sendResult);
		}
		producer.shutdown();
	}

}