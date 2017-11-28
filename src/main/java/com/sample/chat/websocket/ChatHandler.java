package com.sample.chat.websocket;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatHandler extends TextWebSocketHandler {

	Map<String, WebSocketSession> establishedSessionMap = new HashMap<String, WebSocketSession>();
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		String[] payloadItems = payload.split(":");
		String protocol = payloadItems[0];
		
		if ("CONNECT".equals(protocol)) {
			String requestId = getSessionUserId(session);
			String responseId = payloadItems[1];
			
			session.sendMessage(new TextMessage("ON_CHAT:" + responseId));
			establishedSessionMap.get(responseId).sendMessage(new TextMessage("ON_CHAT:" + requestId));
		} else if ("CLOSE".equals(protocol)) {
			String requestId = getSessionUserId(session);
			String responseId = payloadItems[1];
			
			session.sendMessage(new TextMessage("OFF_CHAT:" + responseId));
			establishedSessionMap.get(responseId).sendMessage(new TextMessage("OFF_CHAT:" + requestId));
		} else if ("MSG".equals(protocol)) {
			String messageSendId = getSessionUserId(session);
			String messageReceiveId = payloadItems[1];
			String chatMessage = payloadItems[2];
			
			session.sendMessage(new TextMessage("CHAT_MSG:" + messageReceiveId + ":" + messageSendId + ":" + chatMessage));
			establishedSessionMap.get(messageReceiveId).sendMessage(new TextMessage("CHAT_MSG:" + messageSendId + ":" + messageSendId + ":" + chatMessage));
		}
	}
	
	private void sendAllEstablishedUsers() {
		Collection<WebSocketSession> sessions = establishedSessionMap.values();
		for (WebSocketSession session : sessions) {
			try {
				session.sendMessage(new TextMessage("ON_USERS:" + getAllEstablishedUsers()));
			} catch (IOException e) {}
		}
	}
	
	private void sendClosedUsers(String userId) {
		Collection<WebSocketSession> sessions = establishedSessionMap.values();
		for (WebSocketSession session : sessions) {
			try {
				session.sendMessage(new TextMessage("OFF_USER:" + userId));
			} catch (IOException e) {}
		}
	}

	private String getSessionUserId(WebSocketSession session) {
		Map<String, Object> attributes = session.getAttributes();
		return (String) attributes.get("LOGIN_USER_ID");
	}
	
	private String getAllEstablishedUsers() {
		return StringUtils.collectionToDelimitedString(establishedSessionMap.keySet(), ",");
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		establishedSessionMap.put(getSessionUserId(session), session);
		sendAllEstablishedUsers();
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		establishedSessionMap.remove(getSessionUserId(session));
		sendClosedUsers(getSessionUserId(session));
	}
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		establishedSessionMap.remove(getSessionUserId(session));
		sendClosedUsers(getSessionUserId(session));
	}
	
	
}
