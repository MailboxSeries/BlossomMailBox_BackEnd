package org.mailbox.blossom.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.annotation.UserId;
import org.mailbox.blossom.dto.common.ResponseDto;
import org.mailbox.blossom.dto.request.LetterDetailDto;
import org.mailbox.blossom.usecase.ReadLetterUseCase;
import org.mailbox.blossom.usecase.WriteLetterUseCase;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/letters")
public class LetterController {

    private final ReadLetterUseCase readLetterUseCase;
    private final WriteLetterUseCase writeLetterUseCase;

    // 3-2. 편지 목록 확인
    @GetMapping("/list")
    public ResponseDto<?> readLetters(@UserId String userId) {
        return ResponseDto.ok(readLetterUseCase.readLetters(userId));
    }

    // 3-3. 편지 작성
    @PostMapping("")
    public ResponseDto<?> writeLetter(@UserId String userId,
                                      @Valid @RequestPart("body") LetterDetailDto letterDetailDto,
                                      @RequestPart("image") MultipartFile image
                                      ) {
        writeLetterUseCase.writeLetter(userId, letterDetailDto, image);
        return ResponseDto.created(null);
    }

}
