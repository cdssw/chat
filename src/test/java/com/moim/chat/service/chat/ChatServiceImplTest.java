package com.moim.chat.service.chat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.moim.chat.entity.Chat;
import com.moim.chat.event.Sender;
import com.moim.chat.repository.ChatRepository;

/**
 * ChatServiceImplTest.java
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
@RunWith(MockitoJUnitRunner.class)
public class ChatServiceImplTest {

	// 테스트 하고자 하는 class
	private ChatServiceImpl chatServiceImpl;
	
	@Mock private ChatRepository chatRepository;
	
	@Mock
	private Sender sender;
	
	private ChatDto.ChatReq dto = null;

	@Before
	public void setUp() {
		chatServiceImpl = new ChatServiceImpl(chatRepository, sender);
		dto = ChatDto.ChatReq.builder()
				.meetId(1L)
				.leaderName("cdssw@naver.com")
				.username("loh002@naver.com")
				.message("test message")
				.build();
	}

	@Test
	public void testAddMessage() {
		// given
		Chat chat = mock(Chat.class);
		given(chatRepository.save(any(Chat.class))).willReturn(chat);
		given(chat.getInputDt()).willReturn(LocalDateTime.now());
		
		// when
		chatServiceImpl.addMessage(dto);
		
		// then
		verify(sender).send(any(), any());
	}

}
