package org.mailbox.blossom.usecase;

import org.mailbox.blossom.annotation.UseCase;
import org.mailbox.blossom.dto.response.EncodedUserIDDto;

@UseCase
public interface EncodeUserUseCase {
    EncodedUserIDDto encodeUserID(String userId);
}
