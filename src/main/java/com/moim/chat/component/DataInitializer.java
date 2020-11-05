package com.moim.chat.component;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.moim.chat.entity.User;
import com.moim.chat.repository.UserRepository;

import lombok.AllArgsConstructor;

/**
 * DataInitializer.java
 * 
 * @author cdssw
 * @since 2020. 11.05.
 * @description  
 * 
 * <pre>
 * since          author           description
 * ===========    =============    ===========================
 * 2020. 11.05.   cdssw            최초 생성
 * </pre>
 */
@AllArgsConstructor
@Component
public class DataInitializer implements ApplicationRunner {

	private UserRepository userRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		if(userRepository.findByUsername("cdssw@naver.com") == null) {
			User user = User.builder()
					.username("cdssw@naver.com")
					.userNm("Andrew")
					.userNickNm("Blue")
					.phone("010-1111-1111")
					.build();
			userRepository.save(user);
		}
		
		if(userRepository.findByUsername("loh002@naver.com") == null) {
			User user = User.builder()
					.username("loh002@naver.com")
					.userNm("Monica")
					.userNickNm("HK")
					.phone("010-2222-2222")
					.build();
			userRepository.save(user);
		}
		
		if(userRepository.findByUsername("kimkh093@nate.com") == null) {
			User user = User.builder()
					.username("kimkh093@nate.com")
					.userNm("김규현")
					.userNickNm("Developer")
					.phone("010-5555-5555")					
					.build();
			userRepository.save(user);
		}		
		
		if(userRepository.findByUsername("michael@naver.com") == null) {
			User user = User.builder()
					.username("michael@naver.com")
					.userNm("Michael")
					.userNickNm("Tester")
					.phone("010-3333-3333")
					.build();
			userRepository.save(user);
		}
	}

}
