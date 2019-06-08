package com.leo.springboot.websocket.spring;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MyHandler extends TextWebSocketHandler {

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Integer uid = (Integer) session.getAttributes().get("uid");
		session.sendMessage(new TextMessage(uid + ", 你好！欢迎连接到ws服务"));
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		System.out.println("获取到消息 >> " + message.getPayload());
		// 向客户端发送消息
		session.sendMessage(new TextMessage("消息已收到"));

		if (message.getPayload().equals("10")) {
			for (int i = 0; i < 10; i++) {
				session.sendMessage(new TextMessage("消息 -> " + i));
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("断开连接！");
	}
}
