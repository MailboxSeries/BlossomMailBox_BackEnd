package org.mailbox.blossom.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.mailbox.blossom.constant.Constants;
import org.mailbox.blossom.domain.Skin;
import org.mailbox.blossom.dto.common.SelfValidating;

import java.util.List;
import java.util.Optional;

@Getter
public class AttendanceResultDto extends SelfValidating<AttendanceResultDto> {

    @NotNull(message = "getCat must not be null")
    private final Boolean getCat;
    private final Integer catID;

    @Builder
    public AttendanceResultDto(Boolean getCat, Integer catID) {
        this.getCat = getCat;
        this.catID = catID;
        validateSelf();
    }

    public static AttendanceResultDto of(List<Skin> skinList) {
        Optional<Skin> catSkinOptional = skinList.stream()
                .filter(skin -> skin.getName().contains(Constants.ANIMAL_SKIN))
                .findFirst();

        Boolean foundCat = catSkinOptional.isPresent();
        Integer foundCatId = catSkinOptional.map(Skin::getArrayId).orElse(null);

        return AttendanceResultDto.builder()
                .getCat(foundCat)
                .catID(foundCatId)
                .build();
    }
}