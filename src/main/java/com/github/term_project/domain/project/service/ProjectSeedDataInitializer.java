package com.github.term_project.domain.project.service;

import com.github.term_project.domain.project.entity.EmploymentType;
import com.github.term_project.domain.project.entity.Project;
import com.github.term_project.domain.project.entity.RecruitStatus;
import com.github.term_project.domain.project.repository.ProjectRepository;
import com.github.term_project.domain.user.entity.User;
import com.github.term_project.domain.user.repository.UserRepository;
import com.github.term_project.global.error.BusinessException;
import com.github.term_project.global.error.ErrorCode;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(2)
@RequiredArgsConstructor
public class ProjectSeedDataInitializer implements ApplicationRunner {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (projectRepository.count() > 0) {
            return;
        }

        LocalDate today = LocalDate.now();
        User defaultClient = userRepository.findByLoginId("client1")
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, "client1 seed user is required."));

        projectRepository.saveAll(List.of(
                buildOutsourcingProject(
                        defaultClient,
                        "브랜드 사이트 리뉴얼 및 관리자 페이지 구축",
                        "서울 강남구",
                        RecruitStatus.OPEN,
                        4000,
                        5000,
                        45,
                        7,
                        today.plusDays(13),
                        today.minusDays(1),
                        1,
                        "계약 후 1주 이내",
                        "도급 프로젝트",
                        "상세기획 보유",
                        "온라인",
                        """
                        ※ 프로젝트 진행 방식
                        - 프리모아 메인 레퍼런스를 참고한 기업형 브랜드 사이트 리뉴얼 프로젝트입니다.
                        - 메인, 회사 소개, 서비스 소개, 문의, 관리자 페이지까지 포함한 범위입니다.
                        - 계약 후 1주 이내 킥오프 예정이며 주 2회 온라인 미팅으로 진행합니다.
                        - 기획서와 사이트맵은 보유 중이며, 화면 디테일과 문구 정리는 협의가 필요합니다.
                        ────────────────────────

                        ※ 프로젝트의 현재 상황
                        - 기존 사이트는 정보 전달력과 신뢰감이 부족해 전면 리뉴얼이 필요한 상태입니다.
                        - 회사 소개, 서비스 신뢰 요소, 문의 전환 동선, 관리자 운영 편의성까지 함께 개선하려고 합니다.
                        - 디자인 방향은 깔끔한 기업형 톤을 원하며 반응형 대응이 필수입니다.
                        ────────────────────────

                        ※ 상세한 업무 내용
                        1. 브랜드 사이트 리뉴얼
                        - 메인 페이지, 회사 소개, 서비스 소개, 문의 페이지 구성
                        - 실제 레퍼런스와 유사한 정보 배치 및 시각 흐름 구성
                        - 모바일 / 태블릿 / 데스크톱 반응형 대응

                        2. 관리자 페이지
                        - 문의 내역 조회 및 상태 관리
                        - 간단한 프로젝트 / 공지 콘텐츠 관리
                        - 메인 노출 섹션 수정 기능 검토

                        3. 협업 방식
                        - Figma 기반 화면 검토
                        - 프론트 / 백엔드 역할 분리 가능
                        - 퍼블리싱 품질과 관리자 사용성 모두 중요
                        ────────────────────────

                        ※ 우대 사항
                        - 기업형 사이트 리뉴얼 경험
                        - 관리자 페이지 구축 경험
                        - React + Spring Boot 기반 협업 경험
                        - 프리모아 유사 레이아웃 구현 경험
                        """,
                        """
                        - 주 2회 온라인 미팅 진행
                        - 피드백 라운드는 화면 단위로 빠르게 정리
                        - 관리자 기능은 핵심 기능부터 우선 구현
                        """,
                        "프리모아 메인 레퍼런스를 참고한 기업형 브랜드 사이트와 관리자 페이지 구축 프로젝트입니다.",
                        List.of("개발", "디자인", "기획"),
                        List.of("React", "Spring Boot", "Figma")),
                buildOutsourcingProject(
                        defaultClient,
                        "공공기관 예약 시스템 고도화",
                        "서울 중구",
                        RecruitStatus.OPEN,
                        6000,
                        7200,
                        60,
                        11,
                        today.plusDays(5),
                        today.minusDays(2),
                        2,
                        "6월 셋째 주",
                        "도급 프로젝트",
                        "와이어프레임 보유",
                        "온라인",
                        "기존 예약 시스템의 UI 개선과 관리자 통계 화면을 추가합니다. 실사용자 민원이 반영된 상태라 일정 준수가 중요합니다.",
                        "평일 오전 스탠드업과 주간 리뷰가 있습니다.",
                        "예약 플로우와 관리자 통계가 핵심인 중형 규모 프로젝트입니다.",
                        List.of("개발", "기획"),
                        List.of("Vue", "Java", "Oracle")),
                buildResidentProject(
                        defaultClient,
                        "이커머스 운영 프론트엔드 상주 인력",
                        "경기 성남시",
                        RecruitStatus.OPEN,
                        650,
                        90,
                        4,
                        today.plusDays(21),
                        today.minusDays(3),
                        3,
                        "즉시 투입 가능",
                        "상주 프로젝트",
                        "운영 개선안 정리 완료",
                        "판교 오프라인",
                        "운영 중인 이커머스 프론트엔드 유지보수와 프로모션 페이지 제작을 담당합니다. 디자이너와 협업이 잦습니다.",
                        "주 4일 상주, 주 1일 원격으로 조율 가능합니다.",
                        "빠른 일정 대응이 필요한 운영형 상주 프로젝트입니다.",
                        List.of("개발", "디자인"),
                        List.of("React", "TypeScript", "Tailwind CSS")),
                buildOutsourcingProject(
                        defaultClient,
                        "교육 플랫폼 랜딩 페이지와 CMS 제작",
                        "부산 해운대구",
                        RecruitStatus.REVIEWING,
                        2800,
                        3500,
                        35,
                        5,
                        today.plusDays(17),
                        today.minusDays(4),
                        4,
                        "계약 후 10일 내",
                        "도급 프로젝트",
                        "상세기획 보유",
                        "온라인",
                        "교육 콘텐츠를 소개하는 랜딩 페이지와 간단한 CMS를 제작합니다. 반응형 웹과 배포 경험이 필요합니다.",
                        "슬랙 기반 비동기 협업 중심입니다.",
                        "콘텐츠 편집 편의성을 높이는 백오피스가 함께 포함됩니다.",
                        List.of("개발", "디자인"),
                        List.of("Next.js", "Node.js", "PostgreSQL")),
                buildResidentProject(
                        defaultClient,
                        "금융 서비스 QA 자동화 및 운영 지원",
                        "서울 영등포구",
                        RecruitStatus.OPEN,
                        700,
                        120,
                        2,
                        today.plusDays(28),
                        today.minusDays(5),
                        5,
                        "7월 초",
                        "상주 프로젝트",
                        "테스트 시나리오 정리 완료",
                        "여의도 오프라인",
                        "금융 서비스 운영 조직에서 QA 자동화와 배포 검증을 담당할 인력을 찾고 있습니다.",
                        "주요 배포 일정에는 현장 대응이 필요합니다.",
                        "운영 안정성과 테스트 체계 개선을 함께 맡는 역할입니다.",
                        List.of("개발", "기획"),
                        List.of("Selenium", "Java", "Jenkins")),
                buildOutsourcingProject(
                        defaultClient,
                        "헬스케어 앱 백엔드 API 구축",
                        "대전 유성구",
                        RecruitStatus.OPEN,
                        5000,
                        6200,
                        50,
                        8,
                        today.plusDays(9),
                        today.minusDays(6),
                        6,
                        "미팅 후 협의",
                        "도급 프로젝트",
                        "IA 보유",
                        "온라인",
                        "모바일 앱과 연동되는 회원, 예약, 알림 API를 구축합니다. 개인정보 처리 경험이 있으면 좋습니다.",
                        "주 1회 정기 미팅과 이슈 트래킹으로 진행합니다.",
                        "모바일 앱 연동 중심의 API 구축 프로젝트입니다.",
                        List.of("개발"),
                        List.of("Spring Boot", "JPA", "MySQL")),
                buildResidentProject(
                        defaultClient,
                        "B2B SaaS 디자인 시스템 운영",
                        "서울 서초구",
                        RecruitStatus.OPEN,
                        800,
                        150,
                        6,
                        today.plusDays(4),
                        today.minusDays(7),
                        7,
                        "즉시 투입",
                        "상주 프로젝트",
                        "디자인 시스템 초안 보유",
                        "강남 오프라인",
                        "웹 서비스 전반에 쓰일 디자인 시스템을 구축하고 운영 페이지 품질을 끌어올릴 디자이너를 찾습니다.",
                        "오프라인 협업 비중이 높습니다.",
                        "서비스 확장에 맞춰 컴포넌트 기준을 정리하는 역할입니다.",
                        List.of("디자인", "기획"),
                        List.of("Figma", "Design System", "Accessibility")),
                buildOutsourcingProject(
                        defaultClient,
                        "커뮤니티 서비스 검색/알림 기능 추가",
                        "인천 연수구",
                        RecruitStatus.OPEN,
                        3200,
                        4200,
                        30,
                        3,
                        today.plusDays(15),
                        today.minusDays(8),
                        8,
                        "계약 후 2주 내",
                        "도급 프로젝트",
                        "요구사항 정의 완료",
                        "온라인",
                        "기존 커뮤니티 서비스에 통합 검색과 실시간 알림 기능을 추가합니다. Elasticsearch 경험자를 선호합니다.",
                        "주요 기능 단위로 스프린트를 운영합니다.",
                        "검색 품질과 알림 성능 개선이 핵심입니다.",
                        List.of("개발", "기획"),
                        List.of("Spring", "Elasticsearch", "Redis")),
                buildResidentProject(
                        defaultClient,
                        "제조업 ERP 유지보수 백엔드 상주",
                        "울산 남구",
                        RecruitStatus.REVIEWING,
                        550,
                        180,
                        1,
                        today.plusDays(12),
                        today.minusDays(9),
                        9,
                        "다음 주 미팅 예정",
                        "상주 프로젝트",
                        "기존 운영 문서 보유",
                        "울산 오프라인",
                        "레거시 ERP 기능 개선과 장애 대응을 담당합니다. 데이터베이스 튜닝 경험이 있으면 도움이 됩니다.",
                        "현장 커뮤니케이션이 많은 편입니다.",
                        "운영 안정성과 레거시 이해도가 중요한 상주 유지보수 프로젝트입니다.",
                        List.of("개발"),
                        List.of("Spring MVC", "MSSQL", "MyBatis")),
                buildOutsourcingProject(
                        defaultClient,
                        "여행 플랫폼 숙소 상세 페이지 개편",
                        "제주 제주시",
                        RecruitStatus.OPEN,
                        3800,
                        4600,
                        40,
                        6,
                        today.plusDays(19),
                        today.minusDays(10),
                        10,
                        "계약 직후 시작",
                        "도급 프로젝트",
                        "스토리보드 보유",
                        "온라인",
                        "숙소 상세 페이지와 결제 직전 퍼널을 개편해 전환율을 높이는 작업입니다. A/B 테스트 경험이 있으면 좋습니다.",
                        "기획자와 디자이너가 함께 리뷰합니다.",
                        "상세 페이지 전환 개선과 예약 퍼널 최적화가 목표입니다.",
                        List.of("개발", "디자인"),
                        List.of("React", "GA4", "A/B Testing")),
                buildOutsourcingProject(
                        defaultClient,
                        "병원 예약 키오스크 화면 개발",
                        "광주 서구",
                        RecruitStatus.OPEN,
                        2600,
                        3300,
                        25,
                        2,
                        today.plusDays(3),
                        today.minusDays(11),
                        11,
                        "이번 주 내 착수",
                        "도급 프로젝트",
                        "화면 정의서 보유",
                        "온라인",
                        "터치 키오스크에서 사용할 예약/접수 화면을 개발합니다. 접근성과 큰 글자 UI 경험이 중요합니다.",
                        "짧은 일정이라 빠른 피드백이 필요합니다.",
                        "고령층 사용성을 고려한 키오스크 화면 개발 프로젝트입니다.",
                        List.of("개발", "디자인"),
                        List.of("React", "Electron", "Accessibility")),
                buildResidentProject(
                        defaultClient,
                        "콘텐츠 스타트업 데이터 대시보드 분석가",
                        "서울 마포구",
                        RecruitStatus.OPEN,
                        600,
                        75,
                        7,
                        today.plusDays(25),
                        today.minusDays(12),
                        12,
                        "7월 초 합류",
                        "상주 프로젝트",
                        "지표 정의 초안 보유",
                        "홍대 오프라인",
                        "운영 데이터 대시보드 설계와 데이터 파이프라인 정리를 맡을 분석가를 찾습니다.",
                        "주 3회 현장 협업, 나머지는 원격 조율 가능합니다.",
                        "콘텐츠 소비 지표를 정리하고 대시보드를 시각화하는 역할입니다.",
                        List.of("기획", "개발"),
                        List.of("SQL", "Metabase", "Python"))));
    }

    private Project buildOutsourcingProject(
            User client,
            String title,
            String area,
            RecruitStatus recruitStatus,
            int budgetMin,
            int budgetMax,
            int expectedDurationDays,
            int applicationCount,
            LocalDate deadline,
            LocalDate postedAt,
            int displayOrder,
            String kickoffSchedule,
            String progressType,
            String planningStatus,
            String meetingLocation,
            String workDescription,
            String workMethod,
            String summary,
            List<String> categories,
            List<String> skills) {
        return Project.builder()
                .client(client)
                .title(title)
                .area(area)
                .employmentType(EmploymentType.OUTSOURCING)
                .recruitStatus(recruitStatus)
                .budgetMin(budgetMin)
                .budgetMax(budgetMax)
                .monthlyWage(null)
                .expectedDurationDays(expectedDurationDays)
                .applicationCount(applicationCount)
                .deadline(deadline)
                .postedAt(postedAt)
                .displayOrder(displayOrder)
                .kickoffSchedule(kickoffSchedule)
                .progressType(progressType)
                .planningStatus(planningStatus)
                .meetingLocation(meetingLocation)
                .workDescription(workDescription)
                .workMethod(workMethod)
                .summary(summary)
                .categories(categories)
                .skills(skills)
                .build();
    }

    private Project buildResidentProject(
            User client,
            String title,
            String area,
            RecruitStatus recruitStatus,
            int monthlyWage,
            int expectedDurationDays,
            int applicationCount,
            LocalDate deadline,
            LocalDate postedAt,
            int displayOrder,
            String kickoffSchedule,
            String progressType,
            String planningStatus,
            String meetingLocation,
            String workDescription,
            String workMethod,
            String summary,
            List<String> categories,
            List<String> skills) {
        return Project.builder()
                .client(client)
                .title(title)
                .area(area)
                .employmentType(EmploymentType.RESIDENT)
                .recruitStatus(recruitStatus)
                .budgetMin(null)
                .budgetMax(null)
                .monthlyWage(monthlyWage)
                .expectedDurationDays(expectedDurationDays)
                .applicationCount(applicationCount)
                .deadline(deadline)
                .postedAt(postedAt)
                .displayOrder(displayOrder)
                .kickoffSchedule(kickoffSchedule)
                .progressType(progressType)
                .planningStatus(planningStatus)
                .meetingLocation(meetingLocation)
                .workDescription(workDescription)
                .workMethod(workMethod)
                .summary(summary)
                .categories(categories)
                .skills(skills)
                .build();
    }
}
