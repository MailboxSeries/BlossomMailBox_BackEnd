package org.mailbox.blossom.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.domain.Letter;
import org.mailbox.blossom.domain.User;
import org.mailbox.blossom.dto.request.LetterDetailDto;
import org.mailbox.blossom.dto.response.LetterDetailInfoDto;
import org.mailbox.blossom.dto.response.LetterDetailInfoDto.ReplyLetter;
import org.mailbox.blossom.dto.response.LetterDetailInfoDto.SendLetter;
import org.mailbox.blossom.dto.response.LetterListByDateDto;
import org.mailbox.blossom.dto.response.LetterStatusListDto;
import org.mailbox.blossom.dto.type.ErrorCode;
import org.mailbox.blossom.exception.CommonException;
import org.mailbox.blossom.repository.LetterRepository;
import org.mailbox.blossom.repository.UserRepository;
import org.mailbox.blossom.usecase.ReadLetterByDateUseCase;
import org.mailbox.blossom.usecase.ReadLetterDetailUseCase;
import org.mailbox.blossom.usecase.ReadLetterUseCase;
import org.mailbox.blossom.usecase.WriteLetterUseCase;
import org.mailbox.blossom.utility.CryptUtil;
import org.mailbox.blossom.utility.StorageUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mailbox.blossom.constant.Constants.*;

@Service
@RequiredArgsConstructor
public class LetterService implements ReadLetterUseCase, WriteLetterUseCase, ReadLetterByDateUseCase, ReadLetterDetailUseCase {
    private final UserRepository userRepository;
    private final LetterRepository letterRepository;
    private final StorageUtil storageUtil;
    private final CryptUtil cryptUtil;

    @Override
    public LetterStatusListDto readLetters(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        LocalDate userCreatedAt = user.getCreatedAt();
        LocalDate today = LocalDate.now();

        List<LetterStatusListDto.LetterStatus> statusList = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            LocalDate currentDate = START_DATE.plusDays(i);
            String status;
            if (currentDate.isBefore(userCreatedAt)) {
                status = LETTER_DISABLE_STATUS;
            } else if ((currentDate.isAfter(userCreatedAt) || currentDate.equals(userCreatedAt)) && (currentDate.isBefore(today) || currentDate.equals(today))) {
                status = LETTER_ACTIVE_STATUS;
            } else if (currentDate.isAfter(today)) {
                status = LETTER_INACTIVE_STATUS;
            } else {
                status = LETTER_INACTIVE_STATUS;
            }
            statusList.add(LetterStatusListDto.LetterStatus.of(status));
        }

        return LetterStatusListDto.of(statusList);
    }

    @Override
    @Transactional
    public void writeLetter(String userId, LetterDetailDto letterDetailDto, MultipartFile image) throws IllegalBlockSizeException, BadPaddingException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        String fileUrl = null;
        if (image != null && !image.isEmpty()) {
            fileUrl = storageUtil.uploadFile(image);
        }

        if (letterDetailDto.id() == null) {
            User receiver = userRepository.findById(cryptUtil.decrypt(letterDetailDto.receiverId()))
                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
            letterRepository.save(Letter.createLetter(letterDetailDto.sender(), receiver.getNickname(),letterDetailDto.Content(), fileUrl, receiver, null));
        } else {
            Letter letter = letterRepository.findWithUserById(Long.valueOf(letterDetailDto.id()))
                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LETTER));

            letterRepository.save(Letter.createLetter(letter.getReceiver(), letter.getSender(), letterDetailDto.Content(), fileUrl, user, letter));
        }
    }


    @Override
    public LetterListByDateDto readLettersByDate(String userId, Long index) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        LocalDate startDate = START_DATE;
        LocalDate resultDate = startDate.plusDays(index).minusDays(1);

        List<Letter> letterList = letterRepository.findByUserAndCreatedAt(user, resultDate);

        List<LetterListByDateDto.LetterByDate> letterByDates = letterList.stream()
                .map(letter -> LetterListByDateDto.LetterByDate.of(
                        letter.getId().intValue(),
                        letter.getParentLetter() != null,
                        letter.getSender()
                ))
                .collect(Collectors.toList());

        return LetterListByDateDto.of(letterByDates);
    }


    @Override
    public LetterDetailInfoDto readLetterDetail(String userId, Long letterId) {
        Letter letter = letterRepository.findWithUserById(letterId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LETTER));


        return LetterDetailInfoDto.of(
                SendLetter.of(letter.getParentLetter().getImageUrl(), letter.getParentLetter().getContent()),
                ReplyLetter.of(letter.getImageUrl(), letter.getContent(), letter.getSender())
        );

    }
}
