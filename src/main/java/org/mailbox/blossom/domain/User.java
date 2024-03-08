package org.mailbox.blossom.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mailbox.blossom.dto.type.EProvider;
import org.mailbox.blossom.dto.type.ERole;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"serial_id", "provider"}),
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name =  "nickname", nullable = false)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "serial_id", nullable = false)
    private String serialId;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private EProvider provider;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ERole role;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserStatus userStatus;

    @Builder
    public User(String nickname, String password, String serialId, EProvider provider, ERole role) {
        this.nickname = nickname;
        this.password = password;
        this.serialId = serialId;
        this.provider = provider;
        this.role = role;
        this.createdAt = LocalDate.now();
    }

    public static User createUser(String id, String nickname, String password, String serialId) {
        return User.builder()
                .nickname(nickname)
                .password(password)
                .serialId(serialId)
                .build();
    }

}