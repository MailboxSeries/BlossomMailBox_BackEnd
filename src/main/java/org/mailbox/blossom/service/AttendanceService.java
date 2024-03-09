package org.mailbox.blossom.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.constant.Constants;
import org.mailbox.blossom.domain.Item;
import org.mailbox.blossom.domain.Skin;
import org.mailbox.blossom.domain.User;
import org.mailbox.blossom.dto.response.AttendanceResultDto;
import org.mailbox.blossom.dto.response.AttendanceStatusDto;
import org.mailbox.blossom.dto.type.EStatus;
import org.mailbox.blossom.dto.type.ErrorCode;
import org.mailbox.blossom.exception.CommonException;
import org.mailbox.blossom.repository.ItemRepository;
import org.mailbox.blossom.repository.SkinRepository;
import org.mailbox.blossom.repository.UserRepository;
import org.mailbox.blossom.usecase.AttendUseCase;
import org.mailbox.blossom.usecase.CheckAttendanceUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mailbox.blossom.constant.Constants.ANIMAL_SKIN;

@Service
@RequiredArgsConstructor
public class AttendanceService implements CheckAttendanceUseCase, AttendUseCase {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final SkinRepository skinRepository;

    @Override
    public AttendanceStatusDto checkAttendance(String userId) {
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        return AttendanceStatusDto.of(itemRepository.existsByUserIdAndCreatedAtDate(user.getId().toString(), LocalDate.now()));
    }


    @Override
    @Transactional
    public AttendanceResultDto attend(String userId) {
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        List<Skin> userSkins = itemRepository.findItemsWithSkinsByUserId(user.getId())
                .stream()
                .map(Item::getSkin)
                .toList();

        List<Skin> skinList = skinRepository.findAll();

        List<Skin> availableSkins = skinList.stream()
                .filter(skin -> !userSkins.contains(skin))
                .collect(Collectors.toList());

        Collections.shuffle(availableSkins);
        List<Skin> selectedSkinList = new ArrayList<>();

        for (Skin skin : availableSkins) {
            if (selectedSkinList.size() == 2) break;
            if (skin.getName().startsWith(ANIMAL_SKIN) && selectedSkinList.isEmpty()) {
                selectedSkinList.add(skin);
                break;
            } else if (!skin.getName().startsWith(ANIMAL_SKIN)) {
                selectedSkinList.add(skin);
            }
        }

        selectedSkinList.stream().map(skin -> Item.createItem(user, skin, EStatus.UNLOCK)).forEach(itemRepository::save);

        return AttendanceResultDto.of(selectedSkinList);


    }
}
