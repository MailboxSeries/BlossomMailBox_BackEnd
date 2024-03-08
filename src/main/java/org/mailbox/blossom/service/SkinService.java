package org.mailbox.blossom.service;

import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.domain.Item;
import org.mailbox.blossom.domain.Skin;
import org.mailbox.blossom.domain.User;
import org.mailbox.blossom.dto.response.SkinListDto;
import org.mailbox.blossom.dto.type.EStatus;
import org.mailbox.blossom.dto.type.ErrorCode;
import org.mailbox.blossom.exception.CommonException;
import org.mailbox.blossom.repository.ItemRepository;
import org.mailbox.blossom.repository.SkinRepository;
import org.mailbox.blossom.repository.UserRepository;
import org.mailbox.blossom.usecase.ReadSkinUseCase;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SkinService implements ReadSkinUseCase {

    private final SkinRepository skinRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public SkinListDto readSkin(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        List<Item> items = itemRepository.findByUser(user);

        List<Skin> allSkins = skinRepository.findAll();

        Map<String, List<Integer>> having = new HashMap<>();
        Map<String, List<Integer>> unlock = new HashMap<>();
        Map<String, List<Integer>> lock = new HashMap<>();

        allSkins.forEach(skin -> {
            String category = skin.getName().split("[0-9]+")[0];

            having.computeIfAbsent(category, k -> new ArrayList<>());
            unlock.computeIfAbsent(category, k -> new ArrayList<>());
            lock.computeIfAbsent(category, k -> new ArrayList<>());

            Item ownedItem = items.stream()
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

}
