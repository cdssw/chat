package com.moim.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * ResourceServerConfig.java
 * 
 * @author cdssw
 * @since 2020. 10. 22.
 * @description  
 * 
 * <pre>
 * since          author           description
 * ===========    =============    ===========================
 * 2020. 10. 22.   cdssw            최초 생성
 * </pre>
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true) // PreAuthorize 어노테이션 활성화
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String[] WHITE_LIST = {
			"/v2/api-docs",
			"/swagger-resources",
			"/swagger-resources/**",
			"/swagger-ui.html",
			"/webjars/**",
			"/h2-console/**",
			"/*/v2/api-docs",
			"/actuator/**"
	};
	
	// interceptor에서 jwt 파싱을 위해 oauth2 라이브러리를 사용해서 추가했음.
	// history는 gateway를 통해 인증후 호출가능
	// 나머지는 websocket용으로 모두 호출 가능
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable(); // X-Frame-Options 차단 해제
		http.authorizeRequests()
			.antMatchers(WHITE_LIST).permitAll()
			.antMatchers(HttpMethod.POST, "/history").authenticated()
			.antMatchers(HttpMethod.GET, "/unread/**").authenticated()
			.antMatchers(HttpMethod.PUT, "/read/**").authenticated()
			.anyRequest().permitAll();
	}
	
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http.headers().frameOptions().disable(); // X-Frame-Options 차단 해제
//		http.authorizeRequests()
//			.antMatchers(WHITE_LIST).permitAll()
//			.anyRequest().authenticated(); // 모든 요청 호출시 인증되어야 함
//	}	
}
