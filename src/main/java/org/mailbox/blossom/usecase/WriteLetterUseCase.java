package org.mailbox.blossom.usecase;

import org.mailbox.blossom.annotation.UseCase;
import org.mailbox.blossom.dto.request.LetterDetailDto;
import org.mailbox.blossom.dto.response.LetterStatusListDto;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

@UseCase
public interface WriteLetterUseCase {

    void writeLetter(String userId, LetterDetailDto letterDetailDto, MultipartFile image) throws IllegalBlockSizeException, BadPaddingException;
}
