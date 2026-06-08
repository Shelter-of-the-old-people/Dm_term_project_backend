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

        syncUser(missingUsers, "developer1", "1234", "브랜드 웹 프론트엔드", Role.DEVELOPER);
        syncUser(missingUsers, "developer2", "1234", "플랫폼 UI 개발자", Role.DEVELOPER);
        syncUser(missingUsers, "developer3", "1234", "API 백엔드 엔지니어", Role.DEVELOPER);
        syncUser(missingUsers, "developer4", "1234", "서비스기획형 개발자", Role.DEVELOPER);
        syncUser(missingUsers, "developer5", "1234", "앱구축 파트너", Role.DEVELOPER);
        syncUser(missingUsers, "developer6", "1234", "운영 백엔드 전문가", Role.DEVELOPER);
        syncUser(missingUsers, "developer7", "1234", "상주 QA 매니저", Role.DEVELOPER);
        syncUser(missingUsers, "developer8", "1234", "데이터 대시보드 분석가", Role.DEVELOPER);
        syncUser(missingUsers, "client1", "1234", "Client Demo", Role.CLIENT);
        syncUser(missingUsers, "client2", "1234", "Client Alpha", Role.CLIENT);

        if (!missingUsers.isEmpty()) {
            userRepository.saveAll(missingUsers);
        }
    }

    private void syncUser(List<User> missingUsers, String loginId, String password, String nickname, Role role) {
        userRepository.findByLoginId(loginId)
                .ifPresentOrElse(
                        user -> user.updateSeedNickname(nickname),
                        () -> missingUsers.add(User.builder()
                                .loginId(loginId)
                                .password(password)
                                .nickname(nickname)
                                .role(role)
                                .build()));
    }
}
