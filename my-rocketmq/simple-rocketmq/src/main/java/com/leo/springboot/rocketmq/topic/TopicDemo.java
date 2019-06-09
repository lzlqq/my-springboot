package com.leo.springboot.rocketmq.topic;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

/**
 * linux 连不上注意防火墙的问题
 * @author Administrator
 *
 */
public class TopicDemo {
	public static void main(String[] args) throws MQClientException {
		DefaultMQProducer producer = new DefaultMQProducer("leo");
		
		// 设置nameserver的地址
		producer.setNamesrvAddr("192.168.203.105:9876");

		// 启动生产者
		producer.start();

		/**
		 * 创建topic，参数分别是：broker的名称，topic的名称，queue的数量
		 *
		 */
		producer.createTopic("broker_leo_im", "my-topic", 8);

		System.out.println("topic创建成功!");

		producer.shutdown();
	}
}
