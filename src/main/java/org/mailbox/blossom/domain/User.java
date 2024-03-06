package org.mailbox.blossom.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(name = "id")
    private String id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String serialId;

    private String refreshToken;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "user")
    private UserStatus userStatus;

    @Builder
    public User(String id, String nickname, String password, String serialId, String refreshToken, UserStatus userStatus) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.serialId = serialId;
        this.refreshToken = refreshToken;
        this.userStatus = userStatus;
        this.createdAt = LocalDateTime.now();
    }

    public static User createUser(String id, String nickname, String password, String serialId) {
        return User.builder()
                .id(id)
                .nickname(nickname)
                .password(password)
                .serialId(serialId)
                .build();
    }

}