package org.mailbox.blossom.controller;

import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.annotation.UserId;
import org.mailbox.blossom.dto.common.ResponseDto;
import org.mailbox.blossom.usecase.ReadLetterUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/letters")
public class LetterController {

    private final ReadLetterUseCase readLetterUseCase;

    // 3-2. 편지 목록 확인
    @GetMapping("")
    public ResponseDto<?> readLetters(@UserId String userId) {
        return ResponseDto.ok(readLetterUseCase.readLetters(userId));
    }
}
