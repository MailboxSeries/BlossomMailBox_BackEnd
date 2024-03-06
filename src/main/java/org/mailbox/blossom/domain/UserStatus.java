package org.mailbox.blossom.domain;

import jakarta.persistence.*;
import lombok.*;
import org.mailbox.blossom.dto.type.EGender;

@Entity
@Getter
@Table(name = "user_status")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserStatus {

    @Id
    @Column(name = "user_id")
    private String id;

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

}