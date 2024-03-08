package org.mailbox.blossom.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record SkinInfoDto(

        @NotNull(message = "sex must be provided.")
        @Pattern(regexp = "^(man|woman)$", message = "sex must be either 'man' or 'woman'.")
        String sex,
        @NotNull(message = "top must be provided.")
        Integer top,
        @NotNull(message = "bottom must be provided.")
        Integer bottom,
        @NotNull(message = "hair must be provided.")
        Integer hair,
        @NotNull(message = "face must be provided.")
        Integer face,
        @NotNull(message = "animal must be provided.")
        Integer animal,
        @NotNull(message = "rightStore must be provided.")
        Integer rightStore,
        @NotNull(message = "leftStore must be provided.")
        Integer leftStore

) {
}
