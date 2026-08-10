package com.practice.practice_8_9.user;


import com.practice.practice_8_9.user.dto.request.SignUpRequest;
import com.practice.practice_8_9.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signup(SignUpRequest request) {

        // 공백 제거 + 소문자 적용한 이메일
        String email = request.email().trim().toLowerCase(Locale.ROOT);

        // 공백 제거한 닉네임
        String nickname = request.nickname().trim();

        // 이메일, 닉네임 중복 검사
        validateDuplicateEmail(email);
        validateDuplicateNickname(nickname);

        // 비번 암호화
        String passwordHash = passwordEncoder.encode(request.password());

        // 유저 객체 생성
        User user = User.create(
                email,
                passwordHash,
                nickname
        );

        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    private void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "이미 사용중인 이메일입니다."
            );
        }
    }

    private void validateDuplicateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException(
                    "이미 사용중인 닉네임입니다."
            );
        }
    }
}
