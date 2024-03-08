package org.mailbox.blossom.controller;

import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.annotation.UserId;
import org.mailbox.blossom.dto.common.ResponseDto;
import org.mailbox.blossom.usecase.CheckAttendanceUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendances")
public class AttendanceController {

    private final CheckAttendanceUseCase checkAttendanceUseCase;


    @GetMapping("")
    public ResponseDto<?> checkAttendance(@UserId String userId) {
        return ResponseDto.ok(checkAttendanceUseCase.checkAttendance(userId));
    }



}
