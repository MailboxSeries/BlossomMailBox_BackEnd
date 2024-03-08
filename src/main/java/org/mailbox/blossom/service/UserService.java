package org.mailbox.blossom.service;

import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.domain.User;
import org.mailbox.blossom.dto.response.EncodedUserIDDto;
import org.mailbox.blossom.dto.response.UserDetailDto;
import org.mailbox.blossom.dto.type.ErrorCode;
import org.mailbox.blossom.exception.CommonException;
import org.mailbox.blossom.repository.UserRepository;
import org.mailbox.blossom.usecase.ReadUserUseCase;
import org.mailbox.blossom.usecase.EncodeUserUseCase;
import org.mailbox.blossom.utility.CryptUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements EncodeUserUseCase, ReadUserUseCase {
    private final UserRepository userRepository;
    private final CryptUtil cryptUtil;

    @Override
    public EncodedUserIDDto encodeUserID(String userId) {
        try {
            return new EncodedUserIDDto(cryptUtil.encrypt(userId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDetailDto readUser(String encodedUserId) {
        String userId;
        try {
            userId = cryptUtil.decrypt(encodedUserId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));


        return UserDetailDto.fromEntity(user);
    }
}
