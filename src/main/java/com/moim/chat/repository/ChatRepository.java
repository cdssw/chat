package com.moim.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moim.chat.entity.Chat;
import com.moim.chat.repository.custom.ChatCustomRepository;

/**
 * ChatRepository.java
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
public interface ChatRepository extends JpaRepository<Chat, Long>, ChatCustomRepository {
	
	Long countByMeetIdAndReadYnAndReceiver(long meetId, boolean read, String receiver);
	Chat findTop1ByMeetId(long meetId);
	Long findDistinctByMeetId(long meetId);
}
