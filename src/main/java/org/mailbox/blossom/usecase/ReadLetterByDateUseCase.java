package org.mailbox.blossom.usecase;

import org.mailbox.blossom.annotation.UseCase;
import org.mailbox.blossom.annotation.UserId;
import org.mailbox.blossom.dto.response.LetterListByDateDto;

@UseCase
public interface ReadLetterByDateUseCase {

    LetterListByDateDto readLettersByDate(String userId, Long index);
}
