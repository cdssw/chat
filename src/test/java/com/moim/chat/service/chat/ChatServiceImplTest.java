package com.moim.chat.service.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
	private ChatDto.ChatHistoryReq his = null;
	
	@Before
	public void setUp() {
		ModelMapper modelMapper = new ModelMapper();
		chatServiceImpl = new ChatServiceImpl(modelMapper, chatRepository, sender);
		dto = ChatDto.ChatReq.builder()
				.meetId(1L)
				.leaderName("cdssw@naver.com")
				.receiver("loh002@naver.com")
				.message("test message")
				.build();
		
		his = ChatDto.ChatHistoryReq.builder()
				.meetId(1L)
				.sender("cdssw@naver.com")
				.build();		
	}

	@Test
	public void testAddMessage() {
		// given
		Chat chat = mock(Chat.class);
		given(chatRepository.save(any(Chat.class))).willReturn(chat);
		given(chat.getTimeStamp()).willReturn(LocalDateTime.now());
		
		// when
		chatServiceImpl.addMessage(dto);
		
		// then
		verify(sender, times(2)).send(any(), any());
	}

	@Test
	public void testPostHistory() {
		// given
		Chat m1 = mock(Chat.class);
		Chat m2 = mock(Chat.class);
		
		List<Chat> list = Arrays.asList(m1, m2);
		
		Pageable pageable = PageRequest.of(0, 10);
		Page<Chat> pageList = new PageImpl<>(list, pageable, list.size());
		given(chatRepository.findHistory(any(ChatDto.ChatHistoryReq.class), any(), any())).willReturn(pageList);
		
		// when
		Page<ChatDto.Res> res = chatServiceImpl.postHistory(his, "cdssw@naver.com", pageable);
		
		// then
		assertEquals(res.getTotalElements(), 2);
	}
	
	@Test
	public void testGetUnread() {
		// given
		given(chatRepository.countByMeetIdAndReadYnAndReceiver(anyLong(), anyBoolean(), any())).willReturn(10L);
		
		// when
		Long count = chatServiceImpl.getUnread(1L, "cdssw@naver.com");
		
		// then
		assertEquals(count, 10L);
	}
}
