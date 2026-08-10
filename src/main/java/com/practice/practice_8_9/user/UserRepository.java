package com.practice.practice_8_9.user;

import com.practice.practice_8_9.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // email로 user 찾기
    Optional<User> findByEmail(String email);

    // email 중복 검사
    boolean existsByEmail(String email);

    // nickname 중복 검사
    boolean existsByNickname(String nickname);
}
