package org.mailbox.blossom.dto.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.mailbox.blossom.domain.User;
import org.mailbox.blossom.dto.common.SelfValidating;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
public class UserDetailDto extends SelfValidating<UserDetailDto> {
    @NotNull(message = "nickname must be provided.")
    private final String nickname;
    @Min(value = 1, message = "createdDayCnt must be greater than 0.")
    private final Long createdDayCnt;

    @NotNull(message = "sex must be provided.")
    private final String sex;

    @Min(value = 1, message = "top must be greater than 0.")
    private final Integer top;
    @Min(value = 1, message = "hair must be greater than 0.")
    private final Integer hair;
    @Min(value = 1, message = "face must be greater than 0.")
    private final Integer face;
    @Min(value = 1, message = "bottom must be greater than 0.")
    private final Integer bottom;

    @Min(value = 1, message = "animal must be greater than 0.")
    private final Integer animal;
    @Min(value = 1, message = "rightStore must be greater than 0.")
    private final Integer rightStore;
    @Min(value = 1, message = "leftStore must be greater than 0.")
    private final Integer leftStore;

    @Builder
    public UserDetailDto(
            String nickname, Long createdDayCnt,
            String sex, Integer top, Integer hair, Integer face, Integer bottom,
            Integer animal, Integer rightStore, Integer leftStore) {
        this.nickname = nickname;
        this.createdDayCnt = createdDayCnt;

        this.sex = sex;
        this.top = top;
        this.hair = hair;
        this.face = face;
        this.bottom = bottom;

        this.animal = animal;
        this.rightStore = rightStore;
        this.leftStore = leftStore;

        validateSelf();
    }

    public static UserDetailDto fromEntity(User user) {
        return UserDetailDto.builder()
                .nickname(user.getNickname())
                .createdDayCnt(ChronoUnit.DAYS.between(user.getCreatedAt(), LocalDate.now()) + 1)

                .sex(user.getUserStatus().getGender().toString())
                .top(user.getUserStatus().getSkinTop())
                .hair(user.getUserStatus().getSkinHair())
                .face(user.getUserStatus().getSkinFace())
                .bottom(user.getUserStatus().getSkinBottom())

                .animal(user.getUserStatus().getSkinAnimal())
                .rightStore(user.getUserStatus().getSkinRightStore())
                .leftStore(user.getUserStatus().getSkinLeftStore()).build();
    }

}
