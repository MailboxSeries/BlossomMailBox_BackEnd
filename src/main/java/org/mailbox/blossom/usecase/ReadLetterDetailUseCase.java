package org.mailbox.blossom.usecase;

import org.mailbox.blossom.annotation.UseCase;
import org.mailbox.blossom.dto.response.LetterDetailInfoDto;

@UseCase
public interface ReadLetterDetailUseCase {
    LetterDetailInfoDto readLetterDetail(String userId, Long letterId);
}
