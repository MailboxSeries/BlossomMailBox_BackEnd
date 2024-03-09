package org.mailbox.blossom.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mailbox.blossom.annotation.UserId;
import org.mailbox.blossom.dto.common.ResponseDto;
import org.mailbox.blossom.dto.request.LetterDetailDto;
import org.mailbox.blossom.usecase.ReadLetterByDateUseCase;
import org.mailbox.blossom.usecase.ReadLetterDetailUseCase;
import org.mailbox.blossom.usecase.ReadLetterUseCase;
import org.mailbox.blossom.usecase.WriteLetterUseCase;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/letters")
public class LetterController {

    private final ReadLetterUseCase readLetterUseCase;
    private final WriteLetterUseCase writeLetterUseCase;
    private final ReadLetterByDateUseCase readLetterByDateUseCase;
    private final ReadLetterDetailUseCase readLetterDetailUseCase;

    // 3-2. 편지 목록 확인
    @GetMapping("/list")
    public ResponseDto<?> readLetters(@UserId String userId) {
        return ResponseDto.ok(readLetterUseCase.readLetters(userId));
    }

    // 3-3. 편지 작성
    @PostMapping(
            value ="",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<?> writeLetter(@UserId String userId,
                                      @Valid @RequestPart("body") LetterDetailDto letterDetailDto,
                                      @RequestPart("image") MultipartFile image
                                      ) throws IllegalBlockSizeException, BadPaddingException {
        writeLetterUseCase.writeLetter(userId, letterDetailDto, image);
        return ResponseDto.created(null);
    }

    // 3-4. 날짜별 받은 편지 목록 확인
    @GetMapping("")
    public ResponseDto<?> readLettersByDate(@UserId String userId, @RequestParam Long index) {
        return ResponseDto.ok(readLetterByDateUseCase.readLettersByDate(userId, index));
    }

    // 3=5. 해당 받은 편지 확인
    @GetMapping("/{letterId}")
    public ResponseDto<?> readLetterDetail(@UserId String userId, @PathVariable Long letterId) {
        return ResponseDto.ok(readLetterDetailUseCase.readLetterDetail(userId, letterId));
    }

}
