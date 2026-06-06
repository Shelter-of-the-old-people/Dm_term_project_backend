package com.github.term_project.domain.profile.service;

import com.github.term_project.domain.profile.dto.DeveloperProfileUpdateRequest;
import com.github.term_project.domain.profile.repository.DeveloperProfileRepository;
import com.github.term_project.domain.user.entity.Role;
import com.github.term_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(3)
@RequiredArgsConstructor
public class DeveloperProfileSeedDataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final DeveloperProfileRepository developerProfileRepository;
    private final DeveloperProfileService developerProfileService;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (developerProfileRepository.count() > 0) {
            return;
        }

        userRepository.findByLoginId("developer1")
                .filter(user -> user.getRole() == Role.DEVELOPER)
                .ifPresent(user -> developerProfileService.ensureSeedProfile(
                        user,
                        new DeveloperProfileUpdateRequest(
                                java.util.List.of("개발", "기획"),
                                true,
                                true,
                                "서울",
                                "강남구",
                                "개인사업자",
                                6,
                                java.util.List.of("Spring Boot", "React", "B2B"),
                                "Spring Boot와 React 기반의 웹 서비스 구축 경험이 많은 개발자입니다."),
                        null));
    }
}
