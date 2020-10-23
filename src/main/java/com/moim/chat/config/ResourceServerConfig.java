package com.moim.chat.config;

import org.springframework.context.annotation.Configuration;
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

	// interceptor에서 jwt 파싱을 위해 oauth2 라이브러리를 사용해서 추가했음.
	// 실제로 security url 검증을 하지는 않음
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable(); // X-Frame-Options 차단 해제
		http.authorizeRequests().anyRequest().permitAll();
	}
}
