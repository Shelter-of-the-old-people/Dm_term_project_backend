package com.github.term_project.domain.profile.service;

import com.github.term_project.domain.profile.dto.DeveloperProfileUpdateRequest;
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
    private final DeveloperProfileService developerProfileService;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        ensureProfile(
                "developer1",
                new DeveloperProfileUpdateRequest(
                        java.util.List.of("개발", "기획"),
                        true,
                        true,
                        "서울특별시",
                        "강남구",
                        "개인사업자",
                        6,
                        java.util.List.of("React", "Spring Boot", "Admin", "B2B"),
                        "브랜드 사이트와 관리자 페이지를 함께 구축해 온 프론트엔드 개발자입니다. React 기반 UI 설계와 운영 화면 정리에 강점이 있습니다."));
        ensureProfile(
                "developer2",
                new DeveloperProfileUpdateRequest(
                        java.util.List.of("개발", "디자인"),
                        true,
                        false,
                        "서울특별시",
                        "서초구",
                        "프리랜서",
                        5,
                        java.util.List.of("React", "TypeScript", "UX", "모바일웹"),
                        "플랫폼형 서비스의 화면 설계와 사용자 흐름 개선 경험이 많은 UI 개발자입니다. 반응형 화면과 앱 연동형 웹뷰 작업에 익숙합니다."));
        ensureProfile(
                "developer3",
                new DeveloperProfileUpdateRequest(
                        java.util.List.of("개발"),
                        true,
                        true,
                        "경기도",
                        "성남시",
                        "개인사업자",
                        7,
                        java.util.List.of("Java", "Spring Boot", "AWS", "API"),
                        "API 설계와 운영성 중심의 백엔드 개발을 맡아 온 엔지니어입니다. 인증, 배치, 로그 수집, 외부 연동 구조 정리에 강점이 있습니다."));
        ensureProfile(
                "developer4",
                new DeveloperProfileUpdateRequest(
                        java.util.List.of("개발", "기획"),
                        true,
                        true,
                        "서울특별시",
                        "마포구",
                        "개인사업자",
                        8,
                        java.util.List.of("PM", "React", "서비스기획", "운영도구"),
                        "서비스 기획과 화면 구현을 함께 수행하는 개발자입니다. 운영툴, 관리자 화면, 요구사항 정리와 MVP 범위 조정 경험이 많습니다."));
        ensureProfile(
                "developer5",
                new DeveloperProfileUpdateRequest(
                        java.util.List.of("개발", "디자인"),
                        true,
                        false,
                        "서울특별시",
                        "송파구",
                        "프리랜서",
                        6,
                        java.util.List.of("Flutter", "React Native", "Figma", "앱개발"),
                        "앱 구축과 초기 사용자 경험 정리에 강점이 있는 파트너형 개발자입니다. 모바일 MVP, 하이브리드 앱, 서비스 소개 화면 작업 경험이 많습니다."));
        ensureProfile(
                "developer6",
                new DeveloperProfileUpdateRequest(
                        java.util.List.of("개발"),
                        true,
                        true,
                        "서울특별시",
                        "구로구",
                        "개인사업자",
                        9,
                        java.util.List.of("Spring Boot", "MySQL", "Redis", "Batch"),
                        "운영형 서비스의 백엔드 구조와 데이터 처리 흐름을 안정적으로 정리하는 개발자입니다. 배치, 캐시, 운영 API, 모니터링성 개선 경험이 풍부합니다."));
        ensureProfile(
                "developer7",
                new DeveloperProfileUpdateRequest(
                        java.util.List.of("개발"),
                        true,
                        true,
                        "서울특별시",
                        "영등포구",
                        "프리랜서",
                        7,
                        java.util.List.of("QA", "Selenium", "테스트자동화", "운영검증"),
                        "금융과 커머스 영역에서 QA 자동화와 배포 검증을 담당해 온 상주형 QA 매니저입니다. 테스트 케이스 정리와 운영 릴리즈 검수에 강합니다."));
        ensureProfile(
                "developer8",
                new DeveloperProfileUpdateRequest(
                        java.util.List.of("개발", "기획"),
                        true,
                        false,
                        "서울특별시",
                        "금천구",
                        "개인사업자",
                        6,
                        java.util.List.of("SQL", "Dashboard", "Metabase", "데이터분석"),
                        "운영 데이터를 읽기 쉬운 대시보드와 지표 구조로 바꾸는 분석형 개발자입니다. SQL 기반 리포트와 시각화 화면 설계 경험이 많습니다."));
    }

    private void ensureProfile(String loginId, DeveloperProfileUpdateRequest request) {
        userRepository.findByLoginId(loginId)
                .filter(user -> user.getRole() == Role.DEVELOPER)
                .ifPresent(user -> developerProfileService.ensureSeedProfile(user, request, null));
    }
}
