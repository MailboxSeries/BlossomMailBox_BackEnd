package org.mailbox.blossom.security.service;

import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.repository.UserRepository;
import org.mailbox.blossom.security.info.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return null;
    }

    public UserDetails loadUserById(UUID id) {
        UserRepository.UserSecurityForm userSecurityForm = userRepository.findFormById(id)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found!"));

        return UserPrincipal.create(userSecurityForm, null);
    }
}
