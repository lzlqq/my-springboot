package com.leo.springboot.rocketmq.normal;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "spring-my-topic",
	consumerGroup = "spring-consumer-group", 
	selectorExpression = "*", 
	consumeMode = ConsumeMode.CONCURRENTLY)
public class SpringConsumer implements RocketMQListener<String> {

	@Override
	public void onMessage(String msg) {
		System.out.println("接收到消息 -> " + msg);
	}
}
