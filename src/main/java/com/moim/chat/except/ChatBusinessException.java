package com.moim.chat.except;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ChatBusinessException.java
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
public class ChatBusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2891950653809573137L;
	private ErrorCode errorCode; // rest 결과처리를 위한 ErrorCode Enum
}
