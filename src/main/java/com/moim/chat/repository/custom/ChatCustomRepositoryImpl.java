package com.moim.chat.repository.custom;

import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import com.moim.chat.entity.Chat;
import com.moim.chat.entity.QChat;
import com.moim.chat.service.chat.ChatDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * ChatCustomRepositoryImpl.java
 * 
 * @author cdssw
 * @since 2020. 10. 29.
 * @description  
 * 
 * <pre>
 * since          author           description
 * ===========    =============    ===========================
 * 2020. 10. 29.    cdssw            최초 생성
 * </pre>
 */
@Transactional(readOnly = true)
public class ChatCustomRepositoryImpl extends QuerydslRepositorySupport implements ChatCustomRepository {

	final QChat chat = QChat.chat;
	
	public ChatCustomRepositoryImpl() {
		super(Chat.class);
	}
	
	@Override
	public Page<Chat> findHistory(ChatDto.ChatHistoryReq dto, Pageable pageable) {
		BooleanBuilder builder = new BooleanBuilder();
		builder = builder.and(chat.meetId.eq(dto.getMeetId()));
		builder = builder.and(chat.leaderName.eq(dto.getLeaderName()));
		builder = builder.and(chat.username.eq(dto.getUsername()));
		builder = dto.getId() != null ? builder.and(chat.id.lt(dto.getId())) : builder;
		
		JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
		
		// chat 조회 쿼리를 생성
		final JPQLQuery<Chat> query = queryFactory
				.selectFrom(chat)
				.where(builder);
		
		// 페이징 쿼리로 데이터 조회
		final List<Chat> list = getQuerydsl().applyPagination(pageable, query).fetch();
		// 조회결과를 id 순서로 sort
		final Comparator<Chat> comp = (c1, c2) -> Long.compare(c1.getId(), c2.getId());
		list.sort(comp);
		return new PageImpl<>(list, pageable, query.fetchCount());
	}

}
