package com.eddie.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 소셜 로그인 중 반환된 값의 email 이 이미 생성된 사용자인지 아닌지 확인하기 위함.
    Optional<User> findByEmail(String email);
}
