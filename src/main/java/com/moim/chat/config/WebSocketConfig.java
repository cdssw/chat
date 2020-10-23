package com.moim.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.moim.chat.interceptor.FilterChannelInterceptor;

/**
 * WebSocketConfig.java
 * 
 * @author cdssw
 * @since 2020. 10. 22.
 * @description  
 * 
 * <pre>
 * since          author           description
 * ===========    =============    ===========================
 * 2020. 10. 22.    cdssw            최초 생성
 * </pre>
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// topic으로 prefix
		registry.enableSimpleBroker("/topic");
		
		// 메시지 /app으로 시작하는 주소로 보냄
		// controller에서 /message로 등록되어 있음
		// 따라서 클라이언트에서 stomp로 보낼때 /app/message로 보내면 됨
		registry.setApplicationDestinationPrefixes("/app");
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 웹소켓 연결 주소
		registry.addEndpoint("/chat").setAllowedOrigins("*").withSockJS();
	}
	
	// 호출하기전에 inteceptor에서 authorization 토큰을 검사
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(new FilterChannelInterceptor());
	}
}
