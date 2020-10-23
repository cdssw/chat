package com.moim.chat.except;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * NotFoundException.java
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
@AllArgsConstructor
@Getter
public class NotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8333470763693361638L;

	private ErrorCode errorCode; // rest 결과처리를 위한 ErrorCode Enum
}
