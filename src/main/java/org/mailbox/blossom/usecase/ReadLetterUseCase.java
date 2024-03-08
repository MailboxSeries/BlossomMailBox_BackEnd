package org.mailbox.blossom.usecase;

import org.mailbox.blossom.annotation.UseCase;
import org.mailbox.blossom.dto.response.LetterStatusListDto;

@UseCase
public interface ReadLetterUseCase {

    LetterStatusListDto readLetters(String userId);

}
