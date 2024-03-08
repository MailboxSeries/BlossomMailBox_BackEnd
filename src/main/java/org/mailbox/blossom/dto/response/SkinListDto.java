package org.mailbox.blossom.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.mailbox.blossom.dto.common.SelfValidating;

import java.util.List;
import java.util.Map;


import static org.mailbox.blossom.constant.Constants.*;


@Getter
public class SkinListDto extends SelfValidating<SkinListDto> {

    @NotNull(message = "lockSkinCnt must be provided.")
    private final Integer lockSkinCnt;

    @NotNull(message = "hair must be provided.")
    private final Category hair;

    @NotNull(message = "face must be provided.")
    private final Category face;

    @NotNull(message = "top must be provided.")
    private final Category top;

    @NotNull(message = "bottom must be provided.")
    private final Category bottom;

    @NotNull(message = "animal must be provided.")
    private final Category animal;

    @NotNull(message = "rightStore must be provided.")
    private final Category rightStore;

    @NotNull(message = "leftStore must be provided.")
    private final Category leftStore;

    @Builder
    public SkinListDto(
            Integer lockSkinCnt,
            Category hair, Category face, Category top, Category bottom,
            Category animal, Category rightStore, Category leftStore) {
        this.lockSkinCnt = lockSkinCnt;
        this.hair = hair;
        this.face = face;
        this.top = top;
        this.bottom = bottom;
        this.animal = animal;
        this.rightStore = rightStore;
        this.leftStore = leftStore;
        validateSelf();
    }

    public static SkinListDto of(Map<String, List<Integer>> having, Map<String, List<Integer>> unlock, Map<String, List<Integer>> lock) {
        return SkinListDto.builder()
                .lockSkinCnt(lock.values().stream().mapToInt(List::size).sum())
                .hair(Category.builder()
                        .having(having.get(HAIR_SKIN))
                        .unlock(unlock.get(HAIR_SKIN))
                        .lock(lock.get(HAIR_SKIN))
                        .build())
                .face(SkinListDto.Category.builder()
                        .having(having.get(FACE_SKIN))
                        .unlock(unlock.get(FACE_SKIN))
                        .lock(lock.get(FACE_SKIN))
                        .build())
                .top(SkinListDto.Category.builder()
                        .having(having.get(TOP_SKIN))
                        .unlock(unlock.get(TOP_SKIN))
                        .lock(lock.get(TOP_SKIN))
                        .build())
                .bottom(SkinListDto.Category.builder()
                        .having(having.get(BOTTOM_SKIN))
                        .unlock(unlock.get(BOTTOM_SKIN))
                        .lock(lock.get(BOTTOM_SKIN))
                        .build())
                .animal(SkinListDto.Category.builder()
                        .having(having.get(ANIMAL_SKIN))
                        .unlock(unlock.get(ANIMAL_SKIN))
                        .lock(lock.get(ANIMAL_SKIN))
                        .build())
                .rightStore(SkinListDto.Category.builder()
                        .having(having.get(RIGHT_STORE_SKIN))
                        .unlock(unlock.get(RIGHT_STORE_SKIN))
                        .lock(lock.get(RIGHT_STORE_SKIN))
                        .build())
                .leftStore(SkinListDto.Category.builder()
                        .having(having.get(LEFT_STORE_SKIN))
                        .unlock(unlock.get(LEFT_STORE_SKIN))
                        .lock(lock.get(LEFT_STORE_SKIN))
                        .build())
                .build();
    }


    @Getter
    public static class Category extends SelfValidating<SkinListDto> {
        private final List<Integer> having;
        private final List<Integer> unlock;
        private final List<Integer> lock;

        @Builder
        public Category(List<Integer> having, List<Integer> unlock, List<Integer> lock) {
            this.having = having;
            this.unlock = unlock;
            this.lock = lock;
            validateSelf();
        }
    }

}