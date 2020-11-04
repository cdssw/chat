package com.moim.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moim.chat.entity.User;

/**
 * UserRepository.java
 * 
 * @author cdssw
 * @since Apr 12, 2020
 * @description User Table CRUD, 추가적인 데이터 접근이 필요하면 여기에 추가, 다른 서비스에서도 사용하기에 pkg 분리
 * 
 * <pre>
 * since          author           description
 * ===========    =============    ===========================
 * Apr 12, 2020   cdssw            최초 생성
 * </pre>
 */
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
