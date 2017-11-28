package com.sample.chat.websocket;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.sample.chat.model.User;

public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {

		ServletServerHttpRequest ssr = (ServletServerHttpRequest) request;
		HttpServletRequest httpRequest = ssr.getServletRequest();
		
		User user = (User) httpRequest.getSession().getAttribute("LOGIN_USER");
		if (user != null) {
			attributes.put("LOGIN_USER_ID", user.getId());
		}
		
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}
}
