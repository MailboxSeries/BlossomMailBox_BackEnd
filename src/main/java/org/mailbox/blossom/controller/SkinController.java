package org.mailbox.blossom.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.annotation.UserId;
import org.mailbox.blossom.dto.common.ResponseDto;
import org.mailbox.blossom.dto.request.SkinInfoDto;
import org.mailbox.blossom.dto.request.SkinStatusInfoDto;
import org.mailbox.blossom.usecase.ReadSkinUseCase;
import org.mailbox.blossom.usecase.UpdateSkinStatusUserCase;
import org.mailbox.blossom.usecase.UpdateSkinUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/skins")
public class SkinController {

    private final ReadSkinUseCase readSkinUserCase;
    private final UpdateSkinUseCase updateSkinUserCase;
    private final UpdateSkinStatusUserCase updateSkinStatusUserCase;

    // 2-2. 스킨 목록 확인
    @GetMapping("")
    public ResponseDto<?> readSkin(
            @UserId String encodedUserId
    ) {
        return ResponseDto.ok(readSkinUserCase.readSkin(encodedUserId));
    }

    // 2-3. 스킨 장착 상태 변경
    @PutMapping("")
    public ResponseDto<?> changeSkin(
            @UserId String encodedUserId, @Valid @RequestBody SkinInfoDto skinInfoDto
    ) {
        updateSkinUserCase.updateSkin(encodedUserId, skinInfoDto);
        return ResponseDto.ok(null);
    }

    // 2-4. 스킨 상태 변경
    @PatchMapping("")
    public ResponseDto<?> changeSkinStatus(
            @UserId String encodedUserId, @Valid @RequestBody SkinStatusInfoDto skinStatusInfoDto
    ) {
        updateSkinStatusUserCase.updateSkinStatus(encodedUserId, skinStatusInfoDto);
        return ResponseDto.ok(null);
    }

}
