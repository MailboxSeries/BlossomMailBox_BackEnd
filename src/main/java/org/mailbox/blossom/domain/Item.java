package org.mailbox.blossom.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mailbox.blossom.dto.type.EStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skin_id")
    private Skin skin;

    @Builder
    public Item(User user, Skin skin, EStatus status, LocalDate createdAt) {
        this.user = user;
        this.skin = skin;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Item createItem(User user, Skin skin, EStatus status) {
        return Item.builder()
                .user(user)
                .skin(skin)
                .status(status)
                .createdAt(LocalDate.now())
                .build();
    }
}