package org.mailbox.blossom.domain;

import jakarta.persistence.*;
import lombok.*;
import org.mailbox.blossom.dto.type.EGender;

import java.util.UUID;

@Entity
@Getter
@Table(name = "user_status")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserStatus {

    @Id
    @Column(name = "user_id")
    private UUID id;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private EGender gender;

    @Column(name = "skin_top")
    private int skinTop;

    @Column(name = "skin_hair")
    private int skinHair;

    @Column(name = "skin_face")
    private int skinFace;

    @Column(name = "skin_bottom")
    private int skinBottom;

    @Column(name = "skin_left_store")
    private int skinLeftStore;

    @Column(name = "skin_right_store")
    private int skinRightStore;

    @Column(name = "skin_animal")
    private int skinAnimal;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public UserStatus(User user) {
        this.user = user;

        this.gender = EGender.MAN;
        this.skinTop = 1;
        this.skinHair = 1;
        this.skinFace = 1;
        this.skinBottom = 1;
        this.skinLeftStore = 1;
        this.skinRightStore = 1;
        this.skinAnimal = 1;
    }

}