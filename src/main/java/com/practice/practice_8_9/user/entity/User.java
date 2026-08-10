package com.practice.practice_8_9.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


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
    private String password;

    @Column(nullable = false, length = )
    private String nickname;



    @CreatedDate
    private LocalDateTime createdAt;
}
