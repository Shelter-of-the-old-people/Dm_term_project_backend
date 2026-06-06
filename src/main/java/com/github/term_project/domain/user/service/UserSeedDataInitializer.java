package com.github.term_project.domain.user.service;

import com.github.term_project.domain.user.entity.Role;
import com.github.term_project.domain.user.entity.User;
import com.github.term_project.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(1)
@RequiredArgsConstructor
public class UserSeedDataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        List<User> missingUsers = new ArrayList<>();

        ensureUser(missingUsers, "developer1", "1234", "Developer Demo", Role.DEVELOPER);
        ensureUser(missingUsers, "developer2", "1234", "Frontend Demo", Role.DEVELOPER);
        ensureUser(missingUsers, "developer3", "1234", "Backend Demo", Role.DEVELOPER);
        ensureUser(missingUsers, "client1", "1234", "Client Demo", Role.CLIENT);
        ensureUser(missingUsers, "client2", "1234", "Client Alpha", Role.CLIENT);

        if (!missingUsers.isEmpty()) {
            userRepository.saveAll(missingUsers);
        }
    }

    private void ensureUser(List<User> missingUsers, String loginId, String password, String nickname, Role role) {
        if (userRepository.existsByLoginId(loginId)) {
            return;
        }

        missingUsers.add(User.builder()
                .loginId(loginId)
                .password(password)
                .nickname(nickname)
                .role(role)
                .build());
    }
}
