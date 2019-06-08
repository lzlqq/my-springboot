package com.leo.springboot.im.websocket;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leo.springboot.im.dao.MessageDAO;
import com.leo.springboot.im.pojo.Message;
import com.leo.springboot.im.pojo.UserData;

@Component
public class MessageHandler extends TextWebSocketHandler {

	@Autowired
	private MessageDAO messageDAO;

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private static final Map<Long, WebSocketSession> SESSIONS = new HashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Long uid = (Long) session.getAttributes().get("uid");
		// 将当前用户的session放置到map中，后面会使用相应的session通信
		SESSIONS.put(uid, session);
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
		Long uid = (Long) session.getAttributes().get("uid");

		JsonNode jsonNode = MAPPER.readTree(textMessage.getPayload());
		Long toId = jsonNode.get("toId").asLong();
		String msg = jsonNode.get("msg").asText();

		Message message = Message.builder()
				.from(UserData.USER_MAP.get(uid))
				.to(UserData.USER_MAP.get(toId))
				.msg(msg)
				.build();

		// 将消息保存到MongoDB
		message = messageDAO.saveMessage(message);

		// 判断to用户是否在线
		WebSocketSession toSession = SESSIONS.get(toId);
		if (toSession != null && toSession.isOpen()) {
			// TODO 具体格式需要和前端对接
			toSession.sendMessage(new TextMessage(MAPPER.writeValueAsString(message)));
			// 更新消息状态为已读
			messageDAO.updateMessageState(message.getId(), 2);
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		Long uid = (Long) session.getAttributes().get("uid");
		// 将当前用户的session放置到map中，后面会使用相应的session通信
		SESSIONS.remove(uid);
	}
}
