package org.mailbox.blossom.security.service;

import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.domain.Item;
import org.mailbox.blossom.domain.Skin;
import org.mailbox.blossom.domain.User;
import org.mailbox.blossom.domain.UserStatus;
import org.mailbox.blossom.dto.type.EProvider;
import org.mailbox.blossom.dto.type.ERole;
import org.mailbox.blossom.dto.type.EStatus;
import org.mailbox.blossom.repository.ItemRepository;
import org.mailbox.blossom.repository.SkinRepository;
import org.mailbox.blossom.repository.UserRepository;
import org.mailbox.blossom.repository.UserStatusRepository;
import org.mailbox.blossom.security.info.UserPrincipal;
import org.mailbox.blossom.security.info.factory.OAuth2UserInfo;
import org.mailbox.blossom.security.info.factory.OAuth2UserInfoFactory;
import org.mailbox.blossom.utility.GeneratorUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final SkinRepository skinRepository;
    private final ItemRepository itemRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        EProvider provider = EProvider.fromName(userRequest.getClientRegistration().getRegistrationId());
        OAuth2User beforeOAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, beforeOAuth2User.getAttributes());

        UserRepository.UserSecurityForm userSecurityForm = userRepository.findFormBySerialIdAndProvider(userInfo.getId(), provider)
                .orElseGet(() -> {
                    // User 생성
                    User user = userRepository.save(User.builder()
                            .nickname(userInfo.getNickname())
                            .password(bCryptPasswordEncoder.encode(GeneratorUtil.generateRandomPassword()))
                            .serialId(userInfo.getId())
                            .provider(provider)
                            .role(ERole.USER)
                            .build());

                    // UserStatus 생성
                    UserStatus userStatus = userStatusRepository.save(UserStatus.builder()
                            .user(user)
                            .build());

                    // Default Item을 인벤토리에 추가
                    List<Skin> defaultSkins = skinRepository.findByArrayId(1);
                    List<Item> inventories = defaultSkins.stream()
                            .map(skin -> Item.builder()
                                    .user(user)
                                    .skin(skin)
                                    .status(EStatus.HAVING)
                                    .createdAt(LocalDateTime.now().minusDays(1))
                                    .build())
                            .toList();

                    itemRepository.saveAll(inventories);

                    // 현재 유저의 정보를 반환
                    return UserRepository.UserSecurityForm.of(user);
                });

        return UserPrincipal.create(userSecurityForm, beforeOAuth2User.getAttributes());
    }
}
