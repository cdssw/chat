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
import com.moim.chat.entity.QUser;
import com.moim.chat.service.chat.ChatDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
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
	final QUser user = QUser.user;
	
	public ChatCustomRepositoryImpl() {
		super(Chat.class);
	}
	
	@Override
	public Page<Chat> findHistory(ChatDto.ChatHistoryReq dto, String receiver, Pageable pageable) {
		BooleanBuilder builder = new BooleanBuilder();
		builder = builder.and(chat.meetId.eq(dto.getMeetId()));
		builder = builder.and(chat.sender.eq(dto.getSender()).and(chat.receiver.eq(receiver)));
		builder = builder.or(chat.sender.eq(receiver).and(chat.receiver.eq(dto.getSender())));
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

	@Override
	public List<ChatDto.UsersUnreadRes> countUsersUnread(Long meetId, String username, boolean leader) {
		BooleanBuilder builder = new BooleanBuilder();
		builder = builder.and(chat.meetId.eq(meetId));
		
		// 리더의 경우 자신이 보낸것 제외 모두 조회
		// 그외의 경우 수신자가 자신인 경우만 조회
		if(leader) builder.and(chat.sender.ne(username)); 
		else builder.and(chat.receiver.eq(username));
		
		JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
		
		final JPQLQuery<ChatDto.UsersUnreadRes> query = queryFactory
				.select(Projections.bean(ChatDto.UsersUnreadRes.class
						, chat.sender.as("sender")
						, user.userNickNm
						, user.avatarPath
						, new CaseBuilder()
							.when(chat.read.eq(false)).then(chat.sender.count())
							.otherwise(0L).as("count")
						))
				.from(chat)
				.innerJoin(user).on(chat.sender.eq(user.username))
				.where(builder)
				.groupBy(chat.sender);
		
		return query.fetch();
	}

	@Override
	public List<Chat> findUnread(long id, long meetId, String sender, String receiver) {
		BooleanBuilder builder = new BooleanBuilder();
		builder = builder.and(chat.meetId.eq(meetId));
		builder = builder.and(chat.id.loe(id));
		builder = builder.and(chat.sender.eq(sender).and(chat.receiver.eq(receiver)));
		
		JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
		
		// chat 조회 쿼리를 생성
		final JPQLQuery<Chat> query = queryFactory
				.selectFrom(chat)
				.where(builder);
		
		return query.fetch();
	}

	@Override
	public List<String> findChatCount(long meetId) {
		BooleanBuilder builder = new BooleanBuilder();
		builder = builder.and(chat.meetId.eq(meetId));
		builder = builder.and(chat.sender.ne(chat.leaderName));
		
		JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
		
		// chat 조회 쿼리를 생성
		final JPQLQuery<String> query = queryFactory
				.select(Projections.bean(String.class
						, chat.sender
						))
				.from(chat)
				.where(builder)
				.groupBy(chat.sender)
				;
		
		return query.fetch();
	}

	@Override
	public List<ChatDto.Res> findDistinctBySender(String sender) {
		BooleanBuilder builder = new BooleanBuilder();
		builder = builder.and(chat.leaderName.ne(sender));
		builder = builder.and(chat.sender.eq(sender));
		
		JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
		
		// chat 조회 쿼리를 생성
		final JPQLQuery<ChatDto.Res> query = queryFactory
				.select(Projections.bean(ChatDto.Res.class
						, chat.meetId
						))
				.from(chat)
				.where(builder)
				.groupBy(chat.meetId)
				;
		
		return query.fetch();
	}

}
