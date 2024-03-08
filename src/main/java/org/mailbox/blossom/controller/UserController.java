package org.mailbox.blossom.controller;

import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.annotation.UserId;
import org.mailbox.blossom.dto.common.ResponseDto;
import org.mailbox.blossom.usecase.ReadUserUseCase;
import org.mailbox.blossom.usecase.EncodeUserUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final EncodeUserUseCase encodeUserUseCase;
    private final ReadUserUseCase readUserUseCase;

    @PostMapping("")
    public ResponseDto<?> generateEncodingUserID(@UserId String uuid) {
        return ResponseDto.ok(encodeUserUseCase.encodeUserID(uuid));
    }

    @GetMapping("")
    public ResponseDto<?> readUserId(
            @RequestParam(name = "u") String encodedUserId
    ) {
        return ResponseDto.ok(readUserUseCase.readUser(encodedUserId));
    }
}
