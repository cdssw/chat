package com.moim.chat.component;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.moim.chat.except.ErrorCode;
import com.moim.chat.except.NotFoundException;

/**
 * CommonComponent.java
 * 
 * @author cdssw
 * @since 10.22, 2020
 * @description  
 * 
 * <pre>
 * since          author           description
 * ===========    =============    ===========================
 * 10.30, 2020    cdssw            최초 생성
 * </pre>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class CommonComponent {

	// JpaRepository findById 처리를 위한 공통 Method
	@Transactional(readOnly = true) // 성능향상을 위해
	public <T, ID, C> C findById(T t, ID id, Class<C> type) {
		final Optional<C> m = ((JpaRepository)t).findById((Long)id);
		m.orElseThrow(() -> new NotFoundException(ErrorCode.ELEMENT_NOT_FOUND));
		return type.cast(m.get());
	}
	
	// JpaRepository findById 처리를 위한 공통 Method with ErrorCode
	@Transactional(readOnly = true) // 성능향상을 위해
	public <T, ID, C> C findById(T t, ID id, Class<C> type, ErrorCode errorCode) {
		final Optional<C> m = ((JpaRepository)t).findById((Long)id);
		m.orElseThrow(() -> new NotFoundException(errorCode));
		return type.cast(m.get());
	}
}
