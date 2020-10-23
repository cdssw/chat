package com.moim.chat.interceptor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moim.chat.entity.Authorization;

/**
 * FilterChannelInterceptor.java
 * 
 * @author cdssw
 * @since 2020. 10. 23.
 * @description  
 * 
 * <pre>
 * since          author           description
 * ===========    =============    ===========================
 * 2020. 10. 23.    cdssw            최초 생성
 * </pre>
 */
@Order(Ordered.HIGHEST_PRECEDENCE * 99)
public class FilterChannelInterceptor implements ChannelInterceptor {

	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
		if(StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
			Jwt jwt = JwtHelper.decode(headerAccessor.getNativeHeader("Authorization").get(0));
			String claims = jwt.getClaims();
			try {
				Authorization authorization = mapper.readValue(claims, Authorization.class);
				LocalDateTime exp = LocalDateTime.ofInstant(Instant.ofEpochMilli(authorization.getExp() * 1000), ZoneId.systemDefault());
				LocalDateTime now = LocalDateTime.now();
				if(exp.isBefore(now)) {
					throw new MessagingException("Not Authorization");				
				}
			} catch (MessagingException e) {
				throw new MessagingException("Not Authorization");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return message;
	}
}
