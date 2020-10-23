package com.moim.chat.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * JasyptConfig.java
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
@Profile("!test")
@Configuration
public class JasyptConfig {

	private static final String ENCRYPT_KEY = System.getProperty("jasypt.encryptor.password");

	@Bean("jasyptStringEncryptor")
	public StringEncryptor stringEncryptor() {
		
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(ENCRYPT_KEY);
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setPoolSize("1");
		encryptor.setConfig(config);
		return encryptor;
	}
}
