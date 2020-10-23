package com.moim.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JpaAuditingConfig.java
 * 
 * @author cdssw
 * @since 10.22, 2020
 * @description JPA LocalDateTime 자동 업데이트, BaseTimeEntity 참고  
 * 
 * <pre>
 * since          author           description
 * ===========    =============    ===========================
 * 10.30, 2020    cdssw            최초 생성
 * </pre> 
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {

}
