package com.practice.practice_8_9.user.entity;


import com.practice.practice_8_9.user.enums.UserRole;
import com.practice.practice_8_9.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;


@Getter
@Entity
@Table(name = "users",  // 제약조건 이름 지정 -> 중복에러 났을 때 로그 명확하게 뜸
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_users_email",
                        columnNames = "email"
                ),
                @UniqueConstraint(
                        name = "uk_users_nickname",
                        columnNames = "nickname"
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false, length = 30)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserStatus status;

    // 동시에 같은 User를 수정할 때 충돌 감지
    @Version
    private Long version;


    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    // 철회 시점
    private Instant withdrawnAt;


    // 정보 은닉
    private User(
            String email,
            String passwordHash,
            String nickname
    ) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.nickname = nickname;
        // enum
        this.role = UserRole.User;
        this.status = UserStatus.ACTIVE;
    }

    public static User create(
            String email,
            String passwordHash,
            String nickname
    ) {
        return new User (
                email,
                passwordHash,
                nickname
        );
    }

    // 닉네임 변경
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    // 비밀번호  변경
    public void changePassword(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    // 회원 탈퇴 처리
    public void withdraw() {
        this.status = UserStatus.WITHDRAWN;
        this.withdrawnAt = Instant.now();
    }
}
