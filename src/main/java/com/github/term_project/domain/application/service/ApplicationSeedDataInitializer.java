package com.github.term_project.domain.application.service;

import com.github.term_project.domain.application.entity.Application;
import com.github.term_project.domain.application.entity.ApplicationOnsiteLine;
import com.github.term_project.domain.application.repository.ApplicationRepository;
import com.github.term_project.domain.project.entity.EmploymentType;
import com.github.term_project.domain.project.entity.Project;
import com.github.term_project.domain.project.repository.ProjectRepository;
import com.github.term_project.domain.user.entity.User;
import com.github.term_project.domain.user.repository.UserRepository;
import com.github.term_project.global.error.BusinessException;
import com.github.term_project.global.error.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(4)
@RequiredArgsConstructor
public class ApplicationSeedDataInitializer implements ApplicationRunner {

    private final ApplicationRepository applicationRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (projectRepository.count() == 0) {
            return;
        }

        Map<String, User> usersByLoginId = userRepository.findAll().stream()
                .collect(Collectors.toMap(User::getLoginId, Function.identity()));

        Map<Integer, Project> projectsByDisplayOrder = projectRepository.findAll().stream()
                .collect(Collectors.toMap(Project::getDisplayOrder, Function.identity()));

        List<Application> missingApplications = new ArrayList<>();

        seedProjectOne(missingApplications, projectsByDisplayOrder, usersByLoginId);
        seedProjectTwo(missingApplications, projectsByDisplayOrder, usersByLoginId);
        seedProjectThree(missingApplications, projectsByDisplayOrder, usersByLoginId);
        seedProjectFive(missingApplications, projectsByDisplayOrder, usersByLoginId);
        seedProjectSix(missingApplications, projectsByDisplayOrder, usersByLoginId);
        seedProjectEight(missingApplications, projectsByDisplayOrder, usersByLoginId);
        seedProjectTen(missingApplications, projectsByDisplayOrder, usersByLoginId);
        seedProjectEleven(missingApplications, projectsByDisplayOrder, usersByLoginId);
        seedProjectTwelve(missingApplications, projectsByDisplayOrder, usersByLoginId);

        if (!missingApplications.isEmpty()) {
            applicationRepository.saveAll(missingApplications);
        }

        projectRepository.findAll().forEach(project ->
                project.syncApplicationCount(applicationRepository.findAllByProject_IdOrderByCreatedAtDesc(project.getId()).size()));
    }

    private void seedProjectOne(
            List<Application> missingApplications,
            Map<Integer, Project> projectsByDisplayOrder,
            Map<String, User> usersByLoginId) {

        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                1,
                "developer4",
                32,
                4700,
                """
                        <프로젝트 진행 제안>
                        브랜드 사이트 리뉴얼과 관리자 페이지 구축 범위를 기준으로 메인 화면, 회사 소개, 문의 전환 동선, 관리자 게시 영역을 단계적으로 정리해 진행하겠습니다.

                        <관련 경험 및 강점>
                        기업형 웹사이트와 관리자 화면을 함께 구축한 경험이 많아 정보 구조 정리, 반응형 구현, 운영 화면 연동까지 안정적으로 맡을 수 있습니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                1,
                "developer5",
                36,
                5200,
                """
                        <프로젝트 진행 제안>
                        첫 화면의 신뢰감과 문의 전환율 개선을 목표로 사용자 동선과 핵심 섹션 우선순위를 정리해 UI 중심으로 빠르게 구현하겠습니다.

                        <관련 경험 및 강점>
                        소개형 사이트와 서비스 랜딩 화면 경험이 많아 CTA 배치, 브랜드 톤 정리, 반응형 화면 구현에 강점이 있습니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                1,
                "developer6",
                34,
                4950,
                """
                        <프로젝트 진행 제안>
                        프론트 리뉴얼과 함께 문의 데이터 관리용 관리자 기능을 연결해 운영자가 바로 쓸 수 있는 수준까지 묶어서 구축하겠습니다.

                        <관련 경험 및 강점>
                        운영 API와 관리자 기능 경험이 많아 화면 구현과 데이터 흐름을 함께 고려한 구조로 제안드릴 수 있습니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                1,
                "developer1",
                38,
                5100,
                """
                        <프로젝트 진행 제안>
                        메인 페이지, 소개 페이지, 문의 폼, 관리자 관리 화면까지 한 흐름으로 묶어 MVP 우선순위에 맞게 구현하겠습니다.

                        <관련 경험 및 강점>
                        React 기반 브랜드 사이트와 관리자 화면을 함께 구축한 경험이 있어 화면 완성도와 운영 편의성을 동시에 챙길 수 있습니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                1,
                "developer2",
                34,
                4650,
                """
                        <프로젝트 진행 제안>
                        첫 방문자에게 필요한 메시지가 자연스럽게 보이도록 핵심 섹션을 재배치하고, 모바일에서도 안정적으로 보이는 구조로 정리하겠습니다.

                        <관련 경험 및 강점>
                        반응형 랜딩 페이지와 기업형 소개 사이트 작업 경험이 많아 깔끔한 화면 정리와 UI 개선에 강합니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                1,
                "developer3",
                41,
                5300,
                """
                        <프로젝트 진행 제안>
                        프론트 리뉴얼과 관리자 API 연결 범위를 함께 설계해 유지보수하기 쉬운 구조로 안정적으로 구축하겠습니다.

                        <관련 경험 및 강점>
                        Spring Boot 기반 백엔드와 React 프론트를 함께 협업한 경험이 많아 문의 데이터 처리와 운영 기능 연결까지 자연스럽게 대응 가능합니다.
                        """);
    }

    private void seedProjectTwo(
            List<Application> missingApplications,
            Map<Integer, Project> projectsByDisplayOrder,
            Map<String, User> usersByLoginId) {

        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                2,
                "developer1",
                24,
                6100,
                """
                        <프로젝트 진행 제안>
                        외국인 대상 사용자 흐름을 고려해 상담, 예약, 운영관리 핵심 기능을 MVP 범위에 맞춰 안정적으로 구현하겠습니다.

                        <관련 경험 및 강점>
                        React 기반 서비스 화면과 Spring Boot API 연동 경험이 있어 회원 흐름과 운영 기능을 한 일정 안에 정리할 수 있습니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                2,
                "developer2",
                28,
                5900,
                """
                        <프로젝트 진행 제안>
                        앱 사용자를 위한 상담, 예약, 운영관리 화면을 명확히 나누고 사용자 경험이 자연스럽게 이어지도록 구현하겠습니다.

                        <관련 경험 및 강점>
                        플랫폼형 서비스 UI 설계 경험이 많아 사용자 화면과 운영 화면의 연결 지점을 잘 정리할 수 있습니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                2,
                "developer3",
                26,
                6400,
                """
                        <프로젝트 진행 제안>
                        사용자용 기능과 관리자 기능을 분리해 구조적으로 설계하고 API 연동 기준을 명확히 하며 개발하겠습니다.

                        <관련 경험 및 강점>
                        인증, 외부 연동, 운영 API 구조 설계 경험이 있어 앱 서비스 초기 골격을 안정적으로 구성할 수 있습니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                2,
                "developer4",
                31,
                6200,
                """
                        <프로젝트 진행 제안>
                        상담, 예약, 운영관리 기능을 우선 중심으로 잡고 향후 확장 가능한 플랫폼 구조를 고려해 MVP를 정리하겠습니다.

                        <관련 경험 및 강점>
                        서비스 기획과 구현을 함께 다뤄 본 경험이 있어 요구사항 정리와 화면 흐름 구조화에 강점이 있습니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                2,
                "developer5",
                29,
                6050,
                """
                        <프로젝트 진행 제안>
                        다국어 사용자 흐름과 앱 MVP 특성을 고려해 핵심 기능부터 빠르게 구현하고 후속 확장 방향까지 함께 정리하겠습니다.

                        <관련 경험 및 강점>
                        앱 MVP와 플랫폼 구축 경험이 많아 사용자용 앱과 운영 화면이 함께 있는 구조에 익숙합니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                2,
                "developer6",
                33,
                6550,
                """
                        <프로젝트 진행 제안>
                        예약, 상담, 운영관리 기능을 MVP 범위로 먼저 정리하고 다국어 확장과 관리자 운영 흐름까지 고려한 구조로 개발하겠습니다.

                        <관련 경험 및 강점>
                        운영 API, 인증 구조, 외부 연동 경험이 많아 플랫폼형 앱의 백엔드 골격과 관리자 기능을 안정적으로 설계할 수 있습니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                2,
                "developer7",
                30,
                5750,
                """
                        <프로젝트 진행 제안>
                        출시 전 검수 흐름까지 고려해 앱 핵심 기능별 테스트 포인트를 정리하고 운영 단계에서도 빠르게 검증 가능한 구조를 제안드리겠습니다.

                        <관련 경험 및 강점>
                        플랫폼 서비스 QA 경험과 운영 검수 경험이 있어 개발 단계부터 테스트 기준을 함께 세우는 방식으로 기여할 수 있습니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                2,
                "developer8",
                27,
                6000,
                """
                        <프로젝트 진행 제안>
                        외국인 대상 서비스의 상담, 예약, 운영 데이터 흐름을 고려해 MVP 이후 데이터 분석까지 이어질 수 있는 구조로 정리하겠습니다.

                        <관련 경험 및 강점>
                        운영 데이터 구조화와 대시보드 설계 경험이 있어 관리자 관점에서 필요한 지표와 기록 흐름까지 함께 제안드릴 수 있습니다.
                        """);
    }

    private void seedProjectThree(
            List<Application> missingApplications,
            Map<Integer, Project> projectsByDisplayOrder,
            Map<String, User> usersByLoginId) {

        ensureResidentApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                3,
                "developer7",
                """
                        <투입 가능 시점>
                        즉시 투입 가능하며 커머스 운영 조직과 맞춰 상주 일정 기준으로 화면 유지보수와 이벤트 페이지 작업을 병행할 수 있습니다.

                        <관련 경험 및 작업 방식>
                        운영형 프론트엔드 경험과 QA 검수 경험이 있어 긴급 배포 대응, 운영 이슈 확인, 이벤트 화면 수정에 강점이 있습니다.
                        """,
                List.of(buildResidentLine("개발", "중급(5년 이상 ~ 10년 미만)", 1, 650, 1)));
        ensureResidentApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                3,
                "developer2",
                """
                        <투입 가능 시점>
                        즉시 투입 가능하며 커머스 운영 조직과 맞춰 상주 일정 기준으로 화면 유지보수와 프로모션 페이지 작업을 병행할 수 있습니다.

                        <관련 경험 및 작업 방식>
                        운영형 프론트엔드 경험이 많아 디자이너와의 빠른 커뮤니케이션, 긴급 배포 대응, 반응형 화면 수정에 익숙합니다.
                        """,
                List.of(buildResidentLine("개발", "중급(5년 이상 ~ 10년 미만)", 1, 620, 1)));
        ensureResidentApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                3,
                "developer5",
                """
                        <투입 가능 시점>
                        계약 후 1주 이내 상주 시작이 가능하며 프로모션 화면과 운영 개선 요청을 빠르게 소화하는 역할로 참여할 수 있습니다.

                        <관련 경험 및 작업 방식>
                        커머스와 앱 운영 화면 경험이 있어 이벤트성 화면 제작, 운영 UI 수정, 디자이너 협업 속도에 강점이 있습니다.
                        """,
                List.of(buildResidentLine("개발", "중급(5년 이상 ~ 10년 미만)", 1, 610, 1)));
    }

    private void seedProjectFive(
            List<Application> missingApplications,
            Map<Integer, Project> projectsByDisplayOrder,
            Map<String, User> usersByLoginId) {

        ensureResidentApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                5,
                "developer1",
                """
                        <투입 가능 시점>
                        계약 후 1주 이내 상주 가능하며 QA 자동화와 운영 검증 흐름을 함께 정리할 수 있습니다.

                        <관련 경험 및 작업 방식>
                        운영 서비스 화면과 관리자 도구 경험이 있어 배포 전후 검수 흐름, 체크리스트 정리, 이슈 공유에 익숙합니다.
                        """,
                List.of(buildResidentLine("개발", "중급(5년 이상 ~ 10년 미만)", 1, 520, 1)));
        ensureResidentApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                5,
                "developer2",
                """
                        <투입 가능 시점>
                        계약 즉시 투입 가능하며 릴리즈 일정에 맞춰 QA 자동화와 운영 지원 업무를 병행할 수 있습니다.

                        <관련 경험 및 작업 방식>
                        프론트엔드 품질 검수와 운영 이슈 대응 경험이 많아 자동화 스크립트 보강과 운영 검증 프로세스 정비에 기여할 수 있습니다.
                        """,
                List.of(
                        buildResidentLine("개발", "중급(5년 이상 ~ 10년 미만)", 1, 500, 1),
                        buildResidentLine("기타", "초급(1년 이상 ~ 5년 미만)", 1, 420, 2)));
        ensureResidentApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                5,
                "developer8",
                """
                        <투입 가능 시점>
                        다음 주부터 상주 가능하며 운영 조직과 함께 QA 자동화 범위와 검증 지표를 빠르게 정리할 수 있습니다.

                        <관련 경험 및 작업 방식>
                        테스트 결과 정리와 운영 리포트 경험이 있어 자동화 범위와 운영 검증 체계를 구조적으로 보강할 수 있습니다.
                        """,
                List.of(
                        buildResidentLine("개발", "중급(5년 이상 ~ 10년 미만)", 1, 540, 1),
                        buildResidentLine("기타", "초급(1년 이상 ~ 5년 미만)", 1, 430, 2)));
        ensureResidentApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                5,
                "developer4",
                """
                        <투입 가능 시점>
                        다음 주부터 상주 가능하며 금융 서비스 운영 조직과 함께 QA 자동화 범위 정리와 배포 검증 체계 개선을 바로 시작할 수 있습니다.

                        <관련 경험 및 작업 방식>
                        운영 도구와 관리자 기능을 함께 본 경험이 많아 QA 시나리오 정리, 이슈 분류, 협업용 문서화에 강점이 있습니다.
                        """,
                List.of(buildResidentLine("개발", "중급(5년 이상 ~ 10년 미만)", 1, 560, 1)));
        ensureResidentApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                5,
                "developer7",
                """
                        <투입 가능 시점>
                        계약 즉시 상주 투입 가능하며 금융 서비스 특성에 맞춘 배포 검수와 회귀 테스트 체계를 빠르게 안정화할 수 있습니다.

                        <관련 경험 및 작업 방식>
                        Selenium 기반 자동화와 운영 릴리즈 검수 경험이 많아 테스트 체계 정리와 결과 리포트 고도화에 직접 기여할 수 있습니다.
                        """,
                List.of(buildResidentLine("개발", "고급(10년 이상)", 1, 600, 1)));
    }

    private void seedProjectSix(
            List<Application> missingApplications,
            Map<Integer, Project> projectsByDisplayOrder,
            Map<String, User> usersByLoginId) {

        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                6,
                "developer3",
                42,
                5800,
                """
                        <프로젝트 진행 제안>
                        예약, 회원, 알림 API를 기준으로 우선순위를 나누고 모바일 앱 연동에 필요한 인증과 예외 흐름까지 함께 정리해 구현하겠습니다.

                        <관련 경험 및 강점>
                        Spring Boot 기반 예약/회원 API 구축 경험이 있어 모바일 앱과 연결되는 서버 구조를 안정적으로 정리할 수 있습니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                6,
                "developer6",
                46,
                6100,
                """
                        <프로젝트 진행 제안>
                        API 명세와 예외 처리 기준을 먼저 정리하고, 외부 연동과 운영 로그까지 고려한 구조로 안정적으로 개발하겠습니다.

                        <관련 경험 및 강점>
                        운영형 서비스 백엔드 경험이 많아 보안과 안정성을 함께 챙기며 구현할 수 있습니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                6,
                "developer1",
                39,
                5650,
                """
                        <프로젝트 진행 제안>
                        앱과 연결되는 핵심 API를 명확히 나누고, 프론트에서 쓰기 쉬운 응답 구조 기준으로 서버 구현을 정리하겠습니다.

                        <관련 경험 및 강점>
                        화면 개발과 API 협업 경험이 많아 모바일 클라이언트와 맞물리는 서버 동작을 실무 관점에서 함께 정리할 수 있습니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                6,
                "developer5",
                44,
                5950,
                """
                        <프로젝트 진행 제안>
                        회원, 예약, 알림 흐름을 중심으로 모바일 MVP에 필요한 서버 기능을 우선 구축하고 운영 확장 포인트도 함께 남기겠습니다.

                        <관련 경험 및 강점>
                        앱 프로젝트와 API 연동 경험이 많아 모바일 팀과 빠르게 협업하면서 필요한 서버 구조를 맞출 수 있습니다.
                        """);
    }

    private void seedProjectEight(
            List<Application> missingApplications,
            Map<Integer, Project> projectsByDisplayOrder,
            Map<String, User> usersByLoginId) {

        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                8,
                "developer2",
                27,
                3900,
                """
                        <프로젝트 진행 제안>
                        검색 정확도와 알림 전달 흐름을 분리해 설계하고, 사용자 체감 성능을 기준으로 기능을 빠르게 보강하겠습니다.

                        <관련 경험 및 강점>
                        커뮤니티 서비스 화면 경험과 운영 UI 개선 경험이 있어 검색 결과와 알림 UX를 함께 정리할 수 있습니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                8,
                "developer5",
                33,
                4100,
                """
                        <프로젝트 진행 제안>
                        통합 검색 결과 구조와 실시간 알림 UX를 함께 고려해 운영 중인 서비스와 자연스럽게 이어지도록 구현하겠습니다.

                        <관련 경험 및 강점>
                        모바일/웹 서비스 구축 경험이 있어 사용자 흐름과 운영 기능을 함께 보며 구현할 수 있습니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                8,
                "developer3",
                31,
                4250,
                """
                        <프로젝트 진행 제안>
                        검색 성능 개선과 알림 기능 추가를 분리해 설계하고, 기존 서비스 영향도를 줄이는 방향으로 안정적으로 진행하겠습니다.

                        <관련 경험 및 강점>
                        검색 API, 캐시, 알림 처리 구조 경험이 있어 성능과 운영성을 동시에 고려한 구현이 가능합니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                8,
                "developer8",
                29,
                4050,
                """
                        <프로젝트 진행 제안>
                        검색 로그와 알림 반응 데이터를 함께 볼 수 있게 구조를 정리해 기능 추가 이후 운영 지표까지 챙길 수 있도록 하겠습니다.

                        <관련 경험 및 강점>
                        콘텐츠 서비스 지표 설계 경험이 있어 기능 추가 이후 성과 확인까지 이어지는 흐름을 제안드릴 수 있습니다.
                        """);
    }

    private void seedProjectTen(
            List<Application> missingApplications,
            Map<Integer, Project> projectsByDisplayOrder,
            Map<String, User> usersByLoginId) {

        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                10,
                "developer7",
                35,
                4300,
                """
                        <프로젝트 진행 제안>
                        숙소 상세 페이지의 핵심 전환 구간을 기준으로 정보 구조와 CTA 배치를 다듬고 모바일 중심으로 빠르게 개편하겠습니다.

                        <관련 경험 및 강점>
                        커머스/예약 상세 페이지 검수와 전환 동선 개선 경험이 있어 예약 직전 화면의 품질 보강에 기여할 수 있습니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                10,
                "developer2",
                26,
                4150,
                """
                        <프로젝트 진행 제안>
                        숙소 상세 페이지에서 고객이 실제로 필요한 정보가 먼저 보이도록 구조를 재정리하고 전환 버튼 동선까지 함께 다듬겠습니다.

                        <관련 경험 및 강점>
                        상세 페이지 UI 개선과 모바일 최적화 경험이 많아 예약 전환 중심의 화면 개편에 적합합니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                10,
                "developer4",
                32,
                4450,
                """
                        <프로젝트 진행 제안>
                        콘텐츠 우선순위와 사용자 의사결정 흐름을 기준으로 후기, 편의시설, 예약 CTA 영역을 구조적으로 재정리하겠습니다.

                        <관련 경험 및 강점>
                        서비스 기획과 구현 경험을 함께 갖고 있어 정보량이 많은 상세 페이지를 사용자 중심으로 정리하는 데 강합니다.
                        """);
    }

    private void seedProjectEleven(
            List<Application> missingApplications,
            Map<Integer, Project> projectsByDisplayOrder,
            Map<String, User> usersByLoginId) {

        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                11,
                "developer8",
                18,
                3100,
                """
                        <프로젝트 진행 제안>
                        고령층 사용자도 직관적으로 사용할 수 있도록 큰 버튼과 단순한 입력 흐름을 중심으로 키오스크 화면을 구성하겠습니다.

                        <관련 경험 및 강점>
                        공공/의료형 키오스크 화면 검수와 운영 화면 경험이 있어 짧은 일정 안에서도 사용성을 놓치지 않고 구현할 수 있습니다.
                        """);
        ensureOutsourcingApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                11,
                "developer5",
                20,
                3250,
                """
                        <프로젝트 진행 제안>
                        병원 예약 키오스크에 맞춰 예약 단계별 입력 흐름을 단순화하고 시인성 높은 UI로 화면을 빠르게 정리하겠습니다.

                        <관련 경험 및 강점>
                        터치 중심 화면과 접근성 고려 UI 경험이 있어 병원/공공형 장비 화면에 적합한 제안을 드릴 수 있습니다.
                        """);
    }

    private void seedProjectTwelve(
            List<Application> missingApplications,
            Map<Integer, Project> projectsByDisplayOrder,
            Map<String, User> usersByLoginId) {

        ensureResidentApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                12,
                "developer6",
                """
                        <투입 가능 시점>
                        계약 후 1주 이내 참여 가능하며 운영팀과 맞춰 지표 정의, SQL 정리, 대시보드 구조 설계를 병행할 수 있습니다.

                        <관련 경험 및 작업 방식>
                        운영 데이터를 읽기 쉬운 구조로 정리하고 Metabase 기반 대시보드를 구축한 경험이 많아 실무형 지표 화면 제작에 적합합니다.
                        """,
                List.of(
                        buildResidentLine("기획", "중급(5년 이상 ~ 10년 미만)", 1, 600, 1),
                        buildResidentLine("개발", "초급(1년 이상 ~ 5년 미만)", 1, 480, 2)));
        ensureResidentApplication(
                missingApplications,
                projectsByDisplayOrder,
                usersByLoginId,
                12,
                "developer4",
                """
                        <투입 가능 시점>
                        계약 후 1주 이내 참여 가능하며 운영팀과 지표 정의를 함께 정리하면서 대시보드 구조를 빠르게 안정화할 수 있습니다.

                        <관련 경험 및 작업 방식>
                        관리자 지표 화면과 업무 흐름 설계 경험이 있어 데이터 정리뿐 아니라 실제 운영자가 보는 화면 기준으로 구조를 제안할 수 있습니다.
                        """,
                List.of(
                        buildResidentLine("기획", "중급(5년 이상 ~ 10년 미만)", 1, 580, 1),
                        buildResidentLine("개발", "중급(5년 이상 ~ 10년 미만)", 1, 520, 2)));
    }

    private void ensureOutsourcingApplication(
            List<Application> missingApplications,
            Map<Integer, Project> projectsByDisplayOrder,
            Map<String, User> usersByLoginId,
            int displayOrder,
            String loginId,
            int workDays,
            int bidAmount,
            String content) {

        Project project = projectByDisplayOrder(projectsByDisplayOrder, displayOrder, EmploymentType.OUTSOURCING);
        User developer = userByLoginId(usersByLoginId, loginId);

        if (applicationRepository.existsByProject_IdAndDeveloper_Id(project.getId(), developer.getId())) {
            return;
        }

        missingApplications.add(buildOutsourcingApplication(project, developer, workDays, bidAmount, content));
    }

    private void ensureResidentApplication(
            List<Application> missingApplications,
            Map<Integer, Project> projectsByDisplayOrder,
            Map<String, User> usersByLoginId,
            int displayOrder,
            String loginId,
            String content,
            List<ApplicationOnsiteLine> onsiteLines) {

        Project project = projectByDisplayOrder(projectsByDisplayOrder, displayOrder, EmploymentType.RESIDENT);
        User developer = userByLoginId(usersByLoginId, loginId);

        if (applicationRepository.existsByProject_IdAndDeveloper_Id(project.getId(), developer.getId())) {
            return;
        }

        missingApplications.add(buildResidentApplication(project, developer, content, onsiteLines));
    }

    private Application buildOutsourcingApplication(
            Project project,
            User developer,
            int workDays,
            int bidAmount,
            String content) {

        return Application.builder()
                .project(project)
                .developer(developer)
                .employmentType(EmploymentType.OUTSOURCING)
                .workDays(workDays)
                .bidAmount(bidAmount)
                .content(content)
                .onsiteLines(List.of())
                .build();
    }

    private Application buildResidentApplication(
            Project project,
            User developer,
            String content,
            List<ApplicationOnsiteLine> onsiteLines) {

        return Application.builder()
                .project(project)
                .developer(developer)
                .employmentType(EmploymentType.RESIDENT)
                .workDays(null)
                .bidAmount(null)
                .content(content)
                .onsiteLines(onsiteLines)
                .build();
    }

    private ApplicationOnsiteLine buildResidentLine(
            String skillCategory,
            String careerLevel,
            int headCount,
            int monthlyPay,
            int sortOrder) {

        return ApplicationOnsiteLine.builder()
                .skillCategory(skillCategory)
                .careerLevel(careerLevel)
                .headCount(headCount)
                .monthlyPay(monthlyPay)
                .sortOrder(sortOrder)
                .build();
    }

    private User userByLoginId(Map<String, User> usersByLoginId, String loginId) {
        User user = usersByLoginId.get(loginId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, loginId + " seed user is required.");
        }
        return user;
    }

    private Project projectByDisplayOrder(
            Map<Integer, Project> projectsByDisplayOrder,
            int displayOrder,
            EmploymentType employmentType) {

        Project project = projectsByDisplayOrder.get(displayOrder);
        if (project == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "Seed project displayOrder=" + displayOrder + " is required.");
        }
        if (project.getEmploymentType() != employmentType) {
            throw new BusinessException(
                    ErrorCode.APPLICATION_TYPE_MISMATCH,
                    "Seed project displayOrder=" + displayOrder + " employment type does not match.");
        }
        return project;
    }
}
