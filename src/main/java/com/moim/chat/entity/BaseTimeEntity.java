package com.moim.chat.entity;
/**
 * BaseTimeEntity.java
 * 
 * @author cdssw
 * @since 10.22, 2020
 * @description  기본 TimeEntity, Entity 생성시 이 class를 확장
 * 
 * <pre>
 * since          author           description
 * ===========    =============    ===========================
 * 10.30, 2020    cdssw            최초 생성
 * </pre> 
 */

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
@MappedSuperclass // 확장을 위해서 추가
@EntityListeners(AuditingEntityListener.class) // 시간자동 업데이트를 위해서 추가
public abstract class BaseTimeEntity {

	@CreatedDate // 자동 생성시간
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timeStamp;
}
