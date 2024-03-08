package org.mailbox.blossom.usecase;

import org.mailbox.blossom.annotation.UseCase;
import org.mailbox.blossom.dto.response.EncodedUserIDDto;
import org.mailbox.blossom.dto.response.UserDetailDto;

@UseCase
public interface ReadUserUseCase {
    UserDetailDto readUser(String userId);
}
