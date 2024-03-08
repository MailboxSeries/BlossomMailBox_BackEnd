package org.mailbox.blossom.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "letters")
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender")
    private String sender;

    @Column(name = "content")
    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_letter_id")
    private Letter parentLetter;

    @Builder
    public Letter(String sender, String content, String imageUrl, User user) {
        this.sender = sender;
        this.content = content;
        this.imageUrl = imageUrl;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    public static Letter createLetter(String sender, String content, String imageUrl, User user) {
        return Letter.builder()
                .sender(sender)
                .content(content)
                .imageUrl(imageUrl)
                .user(user)
                .build();
    }

}
