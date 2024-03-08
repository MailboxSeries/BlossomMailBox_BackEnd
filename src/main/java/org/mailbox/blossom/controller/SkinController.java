package org.mailbox.blossom.controller;

import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.annotation.UserId;
import org.mailbox.blossom.dto.common.ResponseDto;
import org.mailbox.blossom.usecase.ReadSkinUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/skins")
public class SkinController {

    private final ReadSkinUseCase readSkinUserCase;

    // 2-2. 스킨 목록 확인
    @GetMapping("")
    public ResponseDto<?> readSkin(
            @UserId String encodedUserId
    ) {
        return ResponseDto.ok(readSkinUserCase.readSkin(encodedUserId));
    }

}
