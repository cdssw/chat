package com.moim.chat.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.moim.chat.entity.Chat;
import com.moim.chat.service.chat.ChatDto;

/**
 * ChatCustomRepository.java
 * 
 * @author cdssw
 * @since 10.22, 2020
 * @description  Chat Table CRUD, 추가적인 데이터 접근이 필요하면 여기에 추가, 다른 서비스에서도 사용하기에 pkg 분리
 * 
 * <pre>
 * since          author           description
 * ===========    =============    ===========================
 * 10.30, 2020    cdssw            최초 생성
 * </pre> 
 */
public interface ChatCustomRepository {
	
	Page<Chat> findHistory(ChatDto.ChatHistoryReq dto, Pageable pageable);
}
