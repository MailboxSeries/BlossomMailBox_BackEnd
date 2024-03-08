package org.mailbox.blossom.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.domain.Item;
import org.mailbox.blossom.domain.Skin;
import org.mailbox.blossom.domain.User;
import org.mailbox.blossom.domain.UserStatus;
import org.mailbox.blossom.dto.request.SkinInfoDto;
import org.mailbox.blossom.dto.request.SkinStatusInfoDto;
import org.mailbox.blossom.dto.response.SkinListDto;
import org.mailbox.blossom.dto.type.EGender;
import org.mailbox.blossom.dto.type.EStatus;
import org.mailbox.blossom.dto.type.ErrorCode;
import org.mailbox.blossom.exception.CommonException;
import org.mailbox.blossom.repository.ItemRepository;
import org.mailbox.blossom.repository.SkinRepository;
import org.mailbox.blossom.repository.UserRepository;
import org.mailbox.blossom.repository.UserStatusRepository;
import org.mailbox.blossom.usecase.ReadSkinUseCase;
import org.mailbox.blossom.usecase.UpdateSkinStatusUserCase;
import org.mailbox.blossom.usecase.UpdateSkinUseCase;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SkinService implements ReadSkinUseCase, UpdateSkinUseCase, UpdateSkinStatusUserCase {

    private final SkinRepository skinRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserStatusRepository userStatusRepository;

    @Override
    public SkinListDto readSkin(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        List<Item> itemList = itemRepository.findByUser(user);

        List<Skin> skinList = skinRepository.findAll();

        Map<String, List<Integer>> having = new HashMap<>();
        Map<String, List<Integer>> unlock = new HashMap<>();
        Map<String, List<Integer>> lock = new HashMap<>();

        skinList.forEach(skin -> {
            String category = skin.getName().split("[0-9]+")[0];

            having.computeIfAbsent(category, k -> new ArrayList<>());
            unlock.computeIfAbsent(category, k -> new ArrayList<>());
            lock.computeIfAbsent(category, k -> new ArrayList<>());

            Item ownedItem = itemList.stream()
                    .filter(item -> item.getSkin().equals(skin))
                    .findFirst()
                    .orElse(null);

            if (ownedItem == null) {
                lock.get(category).add(skin.getArrayId());
            } else {
                switch (ownedItem.getStatus()) {
                    case HAVING:
                        having.get(category).add(skin.getArrayId());
                        break;
                    case UNLOCK:
                        unlock.get(category).add(skin.getArrayId());
                        break;
                    default:
                        lock.get(category).add(skin.getArrayId());
                        break;
                }
            }
        });

        return SkinListDto.of(having, unlock, lock);
    }


    @Override
    @Transactional
    public void updateSkin(String userId, SkinInfoDto skinInfoDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        UserStatus userStatus = userStatusRepository.findById(user.getId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER_STATUS));

        userStatus.updateSkinInfo(
                EGender.valueOf(skinInfoDto.sex()),
                skinInfoDto.top(),
                skinInfoDto.bottom(),
                skinInfoDto.hair(),
                skinInfoDto.face(),
                skinInfoDto.animal(),
                skinInfoDto.rightStore(),
                skinInfoDto.leftStore());

        userStatusRepository.save(userStatus);
    }



    @Override
    @Transactional
    public void updateSkinStatus(String userId, SkinStatusInfoDto skinStatusInfoDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Item item = itemRepository.findItemByTypeAndArrayIdAndUserId(skinStatusInfoDto.type(), skinStatusInfoDto.index(), user.getId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ITEM));

        item.updateStatus(EStatus.UNLOCK);

        itemRepository.save(item);
    }
}
