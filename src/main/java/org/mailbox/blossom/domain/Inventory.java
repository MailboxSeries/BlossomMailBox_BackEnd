package org.mailbox.blossom.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mailbox.blossom.dto.type.EStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "inventories")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Skin skin;

    @Builder
    public Inventory(User user, Skin skin, EStatus status) {
        this.user = user;
        this.skin = skin;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    public static Inventory createInventory(User user, Skin skin, EStatus status) {
        return Inventory.builder()
                .user(user)
                .skin(skin)
                .status(status)
                .build();
    }
}