package com.github.term_project.domain.user.service;

import com.github.term_project.domain.user.entity.Role;
import com.github.term_project.domain.user.entity.User;
import com.github.term_project.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserSeedDataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (userRepository.count() > 0) {
            return;
        }

        userRepository.saveAll(List.of(
                User.builder()
                        .loginId("developer1")
                        .password("1234")
                        .nickname("Developer Demo")
                        .role(Role.DEVELOPER)
                        .build(),
                User.builder()
                        .loginId("client1")
                        .password("1234")
                        .nickname("Client Demo")
                        .role(Role.CLIENT)
                        .build()));
    }
}
