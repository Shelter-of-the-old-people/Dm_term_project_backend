package com.github.term_project.domain.project.service;

import com.github.term_project.domain.project.entity.Project;
import com.github.term_project.domain.project.entity.RecruitStatus;
import com.github.term_project.domain.project.repository.ProjectRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(3)
@RequiredArgsConstructor
public class ProjectReferenceSyncInitializer implements ApplicationRunner {

    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        projectRepository.findAll().forEach(this::syncProject);
    }

    private void syncProject(Project project) {
        Project source = switch (project.getDisplayOrder()) {
            case 1 -> buildBrandProject(project);
            case 2 -> buildForeignerPlatformProject(project);
            case 3 -> buildCommerceResidentProject(project);
            case 4 -> buildEducationLandingProject(project);
            case 5 -> buildQaAutomationProject(project);
            case 6 -> buildHealthcareApiProject(project);
            case 7 -> buildSaasDesignSystemProject(project);
            case 8 -> buildCommunitySearchProject(project);
            case 9 -> buildErpMaintenanceProject(project);
            case 10 -> buildTravelDetailProject(project);
            case 11 -> buildHospitalKioskProject(project);
            case 12 -> buildDashboardAnalystProject(project);
            default -> null;
        };

        if (source != null) {
            project.overwriteSeedData(source);
        }
    }

    private Project buildBrandProject(Project project) {
        return buildOutsourcingProject(
                project,
                "브랜드 사이트 리뉴얼 및 관리자 페이지 구축",
                "서울 강남구",
                RecruitStatus.OPEN,
                4000,
                5000,
                45,
                9,
                LocalDate.of(2026, 6, 20),
                LocalDate.of(2026, 6, 6),
                1,
                "계약 후 1주 이내",
                "도급 프로젝트",
                "상세기획 보유",
                "온라인",
                """
                        ※ 프로젝트 진행 방식
                        - 최초 온라인 미팅을 통해 현재 사이트 문제점과 우선 개선 범위를 함께 정리합니다.
                        - 계약 후 1주 이내 킥오프를 희망하며, 주 2회 정기 미팅과 수시 피드백으로 진행할 예정입니다.
                        - 브랜드 사이트 리뉴얼과 간단한 관리자 페이지 구축을 한 번에 진행하는 형태입니다.
                        - 프리모아 메인 레퍼런스처럼 신뢰감 있는 기업형 톤을 기준으로 화면 구성과 문구 흐름을 정리하고자 합니다.
                        - 기획서, 메뉴 구조, 참고 레퍼런스는 보유 중이며 세부 화면 정의는 협의가 필요합니다.
                        ────────────────────────

                        ※ 프로젝트의 현재 상황
                        - 기존 사이트는 정보 전달력이 약하고 첫 화면에서 회사 신뢰 요소가 충분히 드러나지 않는 상태입니다.
                        - 문의 전환 동선이 단순하지 않고, 운영자가 직접 수정할 수 있는 영역도 제한적이어서 함께 개선하려고 합니다.
                        - 회사 소개, 서비스 소개, 주요 실적/신뢰 요소, 문의 유도 영역을 중심으로 전면 재정비가 필요합니다.
                        - 내부에서 사용할 관리자 페이지는 매우 복잡한 수준이 아니라, 문의 확인과 일부 노출 콘텐츠 수정이 가능한 정도를 우선 희망합니다.
                        ────────────────────────

                        ※ 상세한 업무 내용
                        1. 프로젝트 목적
                        - 기업형 브랜드 사이트를 리뉴얼해 첫 방문자에게 더 신뢰감 있는 인상을 주고, 문의 전환율을 높이는 것이 목표입니다.
                         :: 단순히 화면만 예쁘게 바꾸는 것이 아니라 정보 구조, 문구 흐름, CTA 배치까지 함께 다듬고자 합니다.
                         :: 프리모아 프로젝트 상세/목록에서 느껴지는 안정적인 기업형 분위기를 참고해 정돈된 레이아웃을 원합니다.

                        2. 사이트 리뉴얼 범위
                        - 메인 페이지
                         :: 핵심 서비스 소개, 신뢰 요소, 주요 포트폴리오/실적, 문의 유도 섹션 구성
                         :: 히어로 영역과 하단 CTA가 자연스럽게 이어지는 흐름 필요
                        - 회사 소개 / 서비스 소개 페이지
                         :: 사업 소개, 강점, 진행 프로세스, 기대 효과 등 텍스트 중심 정보를 가독성 있게 재구성
                         :: 이미지, 아이콘, 강조 문구 등을 활용한 정리형 레이아웃 선호
                        - 문의 페이지
                         :: 사용자가 바로 문의를 남길 수 있는 입력 폼 구성
                         :: 문의 접수 이후 관리자 페이지에서 상태를 확인할 수 있어야 함

                        3. 관리자 페이지 요구사항
                        - 문의 목록 조회 및 상태값 관리
                         :: 신규 / 확인중 / 완료 등 간단한 상태 변경 기능
                         :: 문의 상세 확인 및 메모성 관리 기능 검토
                        - 메인 노출 콘텐츠 수정
                         :: 메인 주요 문구, 배너성 섹션, 대표 소개 텍스트 정도는 관리자에서 수정 가능한 형태 희망
                         :: 너무 복잡한 CMS 수준보다는 핵심 운영 편의 기능 중심이면 됩니다.

                        4. 개발 및 구현 조건
                        - 반응형 웹 대응은 필수이며, 데스크톱 기준 완성도뿐 아니라 모바일 화면 정리도 중요합니다.
                        - 퍼블리싱 완성도와 컴포넌트 구조가 깔끔했으면 합니다.
                        - 프론트엔드와 백엔드 역할을 분리해도 좋고, 한 팀에서 함께 진행해도 괜찮습니다.
                        - 배포 이후 기본적인 유지보수와 소스 인수인계가 가능한 구조를 희망합니다.

                        5. 협업 시 기대하는 부분
                        - 기획 의도를 이해하고 더 나은 화면 흐름이나 문구 배치를 제안할 수 있는 분이면 좋겠습니다.
                        - 관리자 사용성까지 함께 고려한 설계를 선호합니다.
                        - Figma 기반 커뮤니케이션과 시안 검토 경험이 있으면 좋습니다.
                        ────────────────────────

                        ※ 참고사항
                        - 기업형 사이트, 브랜드 사이트, 소개형 웹사이트 리뉴얼 경험이 있는 업체를 선호합니다.
                        - 지나치게 화려한 스타트업 스타일보다 차분하고 신뢰감 있는 화면 톤을 원합니다.
                        - 관리자 페이지는 핵심 기능 중심으로 우선 구축하고 추후 확장 가능한 형태면 좋겠습니다.
                        - 향후 유지보수까지 고려해 구조적으로 무리 없는 방식의 구현을 희망합니다.

                        ※ 우대사항
                        - React 기반 기업형 사이트 구축 경험
                        - Spring Boot 또는 백오피스 API 협업 경험
                        - Figma 기반 협업 경험
                        - 브랜드 사이트 리뉴얼 및 관리자 페이지 구축 포트폴리오 보유
                        """,
                """
                        - 주 2회 온라인 미팅 진행
                        - 피드백 라운드는 화면 단위로 빠르게 정리
                        - 관리자 기능은 핵심 기능부터 우선 구현
                        """,
                "프리모아 메인 레퍼런스를 참고한 기업형 브랜드 사이트와 관리자 페이지 구축 프로젝트입니다.",
                List.of("개발", "디자인", "기획"),
                List.of("React", "Spring Boot", "Figma"));
    }

    private Project buildForeignerPlatformProject(Project project) {
        return buildOutsourcingProject(
                project,
                "외국인 대상 플랫폼형 앱 신규 개발",
                "서울 서초구",
                RecruitStatus.OPEN,
                3000,
                5000,
                120,
                51,
                LocalDate.of(2026, 6, 12),
                LocalDate.of(2026, 5, 12),
                2,
                "5월 5째주",
                "신규제작",
                "필요기능 정리",
                "서울 서초구",
                """
                        ※ 프로젝트 진행 방식
                        - 계약 방식 : 도급 외주 프로젝트
                        - 미팅 방식 : 최초 온라인 미팅 진행 예정
                        - 진행 형태 : 기능 정의 및 구조 설계 협의 후 단계별 개발 진행
                        - 개발 대상 : 외국인 대상 플랫폼형 모바일 앱 (Android / iOS)
                        - 회사 위치 : 서울특별시 서초구
                        - 장기적으로 기능 확장 및 플랫폼 운영을 고려한 구조 희망
                        ────────────────────────

                        ※ 프로젝트의 현재 상황
                        - 외국인센터 대상 플랫폼형 앱 서비스 기획 검토 단계입니다.
                        - 내부 IT 전문 인력이 없어 기획 보완부터 함께 가능한 개발 파트너를 희망합니다.
                        - 단순 정보 제공 앱이 아닌, 상담·예약·커뮤니티·운영관리 기능이 포함된 플랫폼 형태를 고려하고 있습니다.
                        - 현재 핵심 요구사항은 정리된 상태이며, MVP 형태로 우선 구축 후 단계적 확장을 검토 중입니다.
                        - Android / iOS 동시 개발을 희망합니다.
                        ────────────────────────

                        ※ 상세한 업무 내용
                        1. 프로젝트 소개
                        - 외국인 사용자를 대상으로 하는 플랫폼형 모바일 앱 개발 프로젝트입니다.
                         :: 상담 신청, 예약, 다국어 지원, 관리자 운영 기능 등을 중심으로 서비스를 구축하며, 추후 커뮤니티 및 실시간 기능 확장 가능성까지 고려하고 있습니다.
                         :: 서비스 초기에는 MVP 중심으로 핵심 기능을 우선 개발하고, 운영 안정화 이후 단계별 기능 확장을 검토 예정입니다.

                        2. 주요 기능 요구사항
                        - 회원 및 권한 관리
                         :: 이메일 / SNS 기반 회원가입 및 로그인
                         :: 일반 사용자 / 상담사 / 관리자 권한 분리
                         :: 개인정보 수집 및 관리 기능
                         :: 이용약관 및 개인정보 처리방침 동의 기능

                        - 상담 및 예약 기능
                         :: 상담 신청 및 문의 등록
                         :: 예약 기능 및 일정 관리
                         :: 상담 이력 관리 기능
                         :: 추후 후기 및 게시글 기능 확장 고려

                        - 다국어 지원
                         :: 앱 UI 다국어 지원 (필수)
                         :: 사용자 작성 콘텐츠 자동 번역 기능
                         :: 번역 API 연동 구조 제안 필요

                        - 관리자 시스템
                         :: 회원 관리
                         :: 문의 및 게시글 관리
                         :: 신고 및 제재 기능
                         :: 운영 로그 및 검토 기능

                        - 플랫폼 운영 기능
                         :: 신고(Report) 기능
                         :: 사용자 차단(Block) 기능
                         :: 관리자 검토 프로세스
                         :: 운영 정책 기반 제재 기능

                        3. 개발 환경 및 기술 제안
                        - Android / iOS 동시 대응 가능한 크로스플랫폼 우선 검토
                        - 서버 및 관리자 페이지 구축 포함 희망
                        - AWS, Firebase 등 운영 구조 제안 가능 업체 우대
                        - 유지보수 및 기능 확장 고려한 아키텍처 설계 필요

                        4. 추가 논의 희망 사항
                        - MVP 우선 개발 방식 적절성 검토
                        - 커뮤니티 기능 추가 시 개발 난이도 및 운영 구조
                        - 자동 번역 API 운영 비용 구조
                        - 향후 실시간 채팅 기능 확장 가능 여부
                        - 유지보수 운영 방식 및 서버 관리 구조 제안
                        ────────────────────────

                        ※ 참고사항
                        - 기획 고도화 및 기능 정의 협업이 가능한 업체를 희망합니다.
                        - 사용자 경험(UI/UX) 관점의 제안이 가능하면 좋습니다.
                        - 플랫폼 서비스 구축 경험이 있는 업체를 우대합니다.
                        - 다국어 서비스 및 외국인 대상 플랫폼 구축 경험이 있다면 공유 부탁드립니다.
                        - 향후 기능 확장 가능성을 고려한 유연한 구조 설계를 희망합니다.

                        ※ 우대사항
                        - 플랫폼 서비스 개발 경험 보유
                        - 다국어 서비스 구축 경험 보유
                        - 상담/예약 시스템 구축 경험 보유
                        - 관리자 운영 시스템 구축 경험 보유
                        - Flutter / React Native 기반 앱 개발 경험 우대
                        - AWS / Firebase 운영 경험 우대
                        """,
                """
                        - 상담 요청, 예약, 다국어 지원, 관리자 운영 기능 포함
                        - 향후 커뮤니티 및 실시간 기능까지 확장 가능한 구조 선호
                        - Flutter, React Native 등 크로스플랫폼 제안 가능
                        """,
                "외국인 대상 상담, 예약, 운영관리 기능이 포함된 플랫폼형 앱 MVP 신규 개발 프로젝트입니다.",
                List.of("개발", "디자인", "기획"),
                List.of("앱개발", "서비스/플랫폼개발", "다국어앱"));
    }

    private Project buildCommerceResidentProject(Project project) {
        return buildResidentProject(
                project,
                "이커머스 운영 프론트엔드 상주 인력",
                "경기 성남시",
                RecruitStatus.OPEN,
                650,
                90,
                4,
                LocalDate.of(2026, 6, 29),
                LocalDate.of(2026, 6, 5),
                3,
                "즉시 투입 가능",
                "상주 프로젝트",
                "운영 개선안 정리 완료",
                "판교 오프라인",
                """
                        ※ 프로젝트 진행 방식
                        - 판교 오피스 기준 주 4일 상주, 주 1일 원격 협업을 기본으로 생각하고 있습니다.
                        - 즉시 투입 가능한 프론트엔드 인력을 우선 검토하고 있으며, 운영 이슈 대응 속도가 중요합니다.
                        - 운영팀, MD, 디자이너와 자주 소통하는 형태라 화면 구현뿐 아니라 일정 대응력도 필요합니다.
                        ────────────────────────

                        ※ 프로젝트의 현재 상황
                        - 운영 중인 이커머스 서비스로, 신규 구축보다는 유지보수와 전환 개선이 섞여 있는 상황입니다.
                        - 프로모션/이벤트 페이지가 수시로 열리고 있고, 장바구니/주문서 구간의 UI 개선 요청도 누적돼 있습니다.
                        - 디자이너는 별도로 있으나, 프론트엔드가 실제 서비스 반영까지 빠르게 연결해야 하는 구조입니다.
                        ────────────────────────

                        ※ 상세한 업무 내용
                        1. 운영 유지보수
                        - 메인/카테고리/상세/장바구니/주문서 구간의 프론트 화면 유지보수
                         :: 운영 이슈 수정, UI 깨짐 대응, 브라우저 이슈 점검
                         :: 기존 코드 기준으로 공통 컴포넌트 정리 및 반복 코드 개선

                        2. 프로모션 및 마케팅 화면 제작
                        - 이벤트 랜딩 페이지, 기획전, 배너 연동 화면 제작
                         :: 반응형 기준으로 빠르게 제작하고 운영 일정에 맞춰 배포
                         :: 디자인 시안을 구현하면서도 기존 서비스 톤과 어긋나지 않게 정리

                        3. 협업 및 운영 대응
                        - QA 대응, 운영 배포 확인, 로그 확인 및 간단한 데이터 태깅 점검
                         :: GA4 또는 마케팅 태그 반영 경험이 있으면 좋습니다.
                         :: 운영팀 요청 사항을 정리하고 우선순위에 맞춰 소화할 수 있어야 합니다.
                        ────────────────────────

                        ※ 참고사항
                        - React + TypeScript 기반 운영 경험이 중요합니다.
                        - Tailwind CSS 또는 유사한 유틸리티 스타일 환경 적응이 빠르면 좋습니다.
                        - 운영 서비스 특성상 갑작스러운 일정 대응이 간헐적으로 발생할 수 있습니다.

                        ※ 우대사항
                        - 이커머스 운영 경험 보유
                        - 이벤트/프로모션 페이지 제작 경험 보유
                        - 프론트엔드 성능 최적화 및 QA 대응 경험 보유
                        """,
                """
                        - 주 4일 상주, 주 1일 원격 가능
                        - 운영팀/디자이너와 긴밀한 커뮤니케이션 필요
                        - React 기반 운영 경험 우대
                        """,
                "빠른 일정 대응이 필요한 이커머스 운영형 프론트엔드 상주 프로젝트입니다.",
                List.of("개발", "디자인"),
                List.of("React", "TypeScript", "Tailwind CSS"));
    }

    private Project buildEducationLandingProject(Project project) {
        return buildOutsourcingProject(
                project,
                "교육 플랫폼 랜딩 페이지와 CMS 제작",
                "부산 해운대구",
                RecruitStatus.REVIEWING,
                2800,
                3500,
                35,
                5,
                LocalDate.of(2026, 6, 25),
                LocalDate.of(2026, 6, 4),
                4,
                "계약 후 10일 내",
                "도급 프로젝트",
                "상세기획 보유",
                "온라인",
                """
                        ※ 프로젝트 진행 방식
                        - 교육 프로그램을 소개하는 대외용 랜딩 페이지와 운영용 간단 CMS를 함께 제작하는 프로젝트입니다.
                        - 계약 후 10일 내 킥오프를 희망하며, 슬랙/노션 기반 비동기 협업 비중이 높은 편입니다.
                        - 화면 구조와 콘텐츠 흐름은 기획안이 있으나, 실제 UX 흐름은 함께 다듬길 원합니다.
                        ────────────────────────

                        ※ 프로젝트의 현재 상황
                        - 교육 과정 홍보용 페이지가 여러 개 흩어져 있어 하나의 구조로 정리할 필요가 있습니다.
                        - 내부 운영자가 강의 소개, 일정, 대표 이미지를 손쉽게 수정할 수 있는 간단 CMS가 필요합니다.
                        - 검색 유입을 고려한 기본 SEO 대응과 빠른 콘텐츠 편집성이 중요합니다.
                        ────────────────────────

                        ※ 상세한 업무 내용
                        1. 랜딩 페이지 제작
                        - 교육 서비스 메인 랜딩, 과정 소개, 문의 유도 영역 구성
                         :: 커리큘럼, 강사진, 후기, FAQ, 신청 CTA 등 주요 섹션 정리
                         :: 모바일 우선 가독성과 반응형 완성도 필요

                        2. 콘텐츠 운영 CMS
                        - 과정 소개 텍스트, 대표 이미지, 모집 상태, 배너 문구 수정 기능
                         :: 관리자 권한에서 핵심 콘텐츠를 직접 수정할 수 있으면 됩니다.
                         :: 복잡한 게시판형 CMS보다 간단한 콘텐츠 편집형 구조를 선호합니다.

                        3. 개발 환경 및 배포
                        - Next.js 기반 SSR/SSG 또는 이에 준하는 구성 검토
                        - Node.js + PostgreSQL 기반 간단 백오피스 구조 가능
                        - 기본 배포/운영 문서 전달 필요
                        ────────────────────────

                        ※ 참고사항
                        - 교육 또는 콘텐츠 서비스 랜딩 제작 경험을 선호합니다.
                        - 퍼블리싱 품질과 콘텐츠 편집 편의성이 모두 중요합니다.
                        - 콘텐츠 수정 주체가 비개발자라 관리자 UX가 직관적이어야 합니다.

                        ※ 우대사항
                        - Next.js 프로젝트 경험 보유
                        - 간단 CMS/백오피스 구축 경험 보유
                        - SEO 및 반응형 최적화 경험 보유
                        """,
                """
                        - 슬랙 기반 비동기 협업 중심
                        - 콘텐츠 편집 편의성 높은 CMS 선호
                        - 배포 및 인수인계 문서 제공 필요
                        """,
                "콘텐츠 편집 편의성을 높이는 백오피스가 함께 포함된 교육 플랫폼 랜딩 제작 프로젝트입니다.",
                List.of("개발", "디자인"),
                List.of("Next.js", "Node.js", "PostgreSQL"));
    }

    private Project buildQaAutomationProject(Project project) {
        return buildResidentProject(
                project,
                "금융 서비스 QA 자동화 및 운영 지원",
                "서울 영등포구",
                RecruitStatus.OPEN,
                700,
                120,
                2,
                LocalDate.of(2026, 7, 6),
                LocalDate.of(2026, 6, 3),
                5,
                "7월 초",
                "상주 프로젝트",
                "테스트 시나리오 정리 완료",
                "여의도 오프라인",
                """
                        ※ 프로젝트 진행 방식
                        - 여의도 오피스 상주 중심으로 금융 서비스 QA 자동화와 운영 배포 검증을 맡는 역할입니다.
                        - 주요 배포 주간에는 현장 대응 비중이 높으며, 릴리즈 전후 체크리스트 수행이 중요합니다.
                        - 개발팀, 운영팀, 기획팀과 함께 테스트 범위를 조율하는 구조입니다.
                        ────────────────────────

                        ※ 프로젝트의 현재 상황
                        - 핵심 회귀 테스트가 수동 위주로 운영되고 있어 자동화 전환이 필요한 상태입니다.
                        - 정기 배포가 월 2회 이상 진행되고 있어, 사전 검증과 사후 모니터링 체계도 함께 정리하려고 합니다.
                        - 테스트 시나리오는 상당 부분 정리되어 있으며, 이를 자동화 스크립트와 리포트 체계로 바꾸는 것이 우선 과제입니다.
                        ────────────────────────

                        ※ 상세한 업무 내용
                        1. QA 자동화 구축
                        - 주요 사용자 플로우 기준 자동화 테스트 시나리오 작성
                         :: 로그인, 계좌/거래, 알림, 회원정보 등 핵심 흐름 중심
                         :: 기존 수동 테스트 케이스를 자동화 가능한 수준으로 재정리

                        2. 배포 검증 및 운영 지원
                        - 스테이징/운영 배포 전후 점검
                         :: 체크리스트 수행, 이슈 리포트 작성, 재현 경로 정리
                         :: 장애 발생 시 원인 후보를 빠르게 분류하고 개발팀에 전달

                        3. 테스트 환경 개선
                        - Jenkins 연동 또는 스케줄 실행 기반 자동화 운영
                        - 테스트 결과 이력 관리 및 주간 리포트 체계 정리
                        - 금융 서비스 특성상 민감 데이터 취급 정책을 준수해야 함
                        ────────────────────────

                        ※ 참고사항
                        - Selenium 기반 자동화 경험이 중요합니다.
                        - 금융 도메인 특성상 꼼꼼한 문서화와 검증 이력이 필요합니다.
                        - 현장 협업과 커뮤니케이션이 많은 역할입니다.

                        ※ 우대사항
                        - 금융/핀테크 QA 경험 보유
                        - Jenkins 또는 CI 기반 테스트 운영 경험 보유
                        - 테스트 리포트 체계 구축 경험 보유
                        """,
                """
                        - 배포 주간 현장 대응 가능 필요
                        - 수동 테스트를 자동화 체계로 전환하는 역할
                        - 금융 도메인 QA 경험 우대
                        """,
                "운영 안정성과 테스트 체계 개선을 함께 맡는 금융 서비스 QA 상주 프로젝트입니다.",
                List.of("개발", "기획"),
                List.of("Selenium", "Java", "Jenkins"));
    }

    private Project buildHealthcareApiProject(Project project) {
        return buildOutsourcingProject(
                project,
                "헬스케어 앱 백엔드 API 구축",
                "대전 유성구",
                RecruitStatus.OPEN,
                5000,
                6200,
                50,
                8,
                LocalDate.of(2026, 6, 17),
                LocalDate.of(2026, 6, 2),
                6,
                "미팅 후 협의",
                "도급 프로젝트",
                "IA 보유",
                "온라인",
                """
                        ※ 프로젝트 진행 방식
                        - 모바일 앱과 연동되는 백엔드 API를 신규 구축하는 프로젝트입니다.
                        - IA와 기본 화면 흐름은 보유 중이며, API 명세와 도메인 구조 설계는 함께 협의할 예정입니다.
                        - 주 1회 정기 미팅과 이슈 트래킹 보드 기반으로 진행합니다.
                        ────────────────────────

                        ※ 프로젝트의 현재 상황
                        - 헬스케어 앱 프론트 화면은 일부 정리되어 있으나, 실제 서비스 운영을 위한 API 계층은 새로 구축해야 합니다.
                        - 회원, 예약, 알림, 관리자 연동 등 주요 기능이 포함되며 개인정보 처리 안정성이 중요합니다.
                        - 추후 관리자 페이지 및 운영 리포트와 연결될 수 있는 구조를 원합니다.
                        ────────────────────────

                        ※ 상세한 업무 내용
                        1. 핵심 API 구축
                        - 회원가입/로그인/권한 처리 API
                        - 예약 생성, 변경, 취소 및 일정 조회 API
                        - 푸시 알림/이메일 등 사용자 알림 연동 구조 검토

                        2. 데이터 및 운영 구조 설계
                        - 의료/상담 예약성 데이터 모델 설계
                        - 관리자 페이지 연동을 고려한 상태값/로그 구조 설계
                        - 개인정보 접근 이력 및 기본 보안 정책 고려

                        3. 기술 스택 및 운영 고려사항
                        - Spring Boot + JPA + MySQL 기반 검토
                        - 추후 파일 업로드, 외부 인증, 통계 API 확장 가능 구조 선호
                        - 기본 배포 구조와 운영 인수인계 문서 필요
                        ────────────────────────

                        ※ 참고사항
                        - 헬스케어 또는 예약 도메인 경험이 있으면 좋습니다.
                        - 단순 CRUD보다는 상태 관리와 운영 편의성을 함께 보는 설계를 선호합니다.
                        - API 문서화 및 테스트 코드 작성 경험이 있으면 우대합니다.

                        ※ 우대사항
                        - Spring Boot 실서비스 구축 경험 보유
                        - 개인정보/민감정보 처리 경험 보유
                        - 모바일 앱 연동 API 설계 경험 보유
                        """,
                """
                        - 주 1회 정기 미팅 진행
                        - API 명세와 도메인 구조 협의 포함
                        - 개인정보 처리 경험 우대
                        """,
                "모바일 앱 연동 중심의 백엔드 API 구축 프로젝트입니다.",
                List.of("개발"),
                List.of("Spring Boot", "JPA", "MySQL"));
    }

    private Project buildSaasDesignSystemProject(Project project) {
        return buildResidentProject(
                project,
                "B2B SaaS 디자인 시스템 운영",
                "서울 서초구",
                RecruitStatus.URGENT,
                800,
                150,
                6,
                LocalDate.of(2026, 6, 12),
                LocalDate.of(2026, 6, 1),
                7,
                "즉시 투입",
                "상주 프로젝트",
                "디자인 시스템 초안 보유",
                "강남 오프라인",
                """
                        ※ 프로젝트 진행 방식
                        - 강남 오피스 상주 중심으로 B2B SaaS 전반에 적용할 디자인 시스템을 정리하고 운영하는 역할입니다.
                        - 프론트엔드 개발팀과 매우 가깝게 협업하며, 실제 제품 화면에 바로 반영되는 구조입니다.
                        - 즉시 투입 가능자를 우선 검토하고 있습니다.
                        ────────────────────────

                        ※ 프로젝트의 현재 상황
                        - 기본 컴포넌트 초안과 Figma 파일은 있으나, 제품 전반에서 일관되게 운영되는 수준까지는 정리되지 않은 상태입니다.
                        - 운영 화면이 빠르게 늘어나면서 컴포넌트 중복과 예외 케이스가 증가하고 있습니다.
                        - 접근성, 폼 패턴, 테이블/대시보드 패턴을 함께 기준화할 필요가 있습니다.
                        ────────────────────────

                        ※ 상세한 업무 내용
                        1. 디자인 시스템 구조화
                        - 컬러, 타이포, 여백, 상태값 등 디자인 토큰 정리
                        - 버튼, 인풋, 셀렉트, 모달, 테이블 등 핵심 컴포넌트 기준 수립
                        - 컴포넌트 사용 규칙 및 예외 케이스 문서화

                        2. 실제 화면 적용
                        - 운영 페이지/설정 페이지/대시보드 화면에 디자인 시스템 반영
                        - 기존 화면을 점진적으로 시스템화하면서 품질 개선
                        - 개발팀과 함께 구현 가능한 수준으로 스펙 정리

                        3. 협업 및 운영
                        - Figma 라이브러리 관리
                        - 접근성/반응형 체크
                        - 신규 화면 요청 시 공통 패턴 우선 검토
                        ────────────────────────

                        ※ 참고사항
                        - 단순 시각 디자인보다 시스템 관점에서 정리하는 역량이 중요합니다.
                        - 개발 협업 경험이 풍부한 디자이너를 선호합니다.
                        - B2B 운영 화면 경험이 있으면 적응이 빠릅니다.

                        ※ 우대사항
                        - 디자인 시스템 구축 경험 보유
                        - Figma 라이브러리 운영 경험 보유
                        - 접근성/운영 UI 경험 보유
                        """,
                """
                        - 강남 오피스 상주 중심
                        - 개발팀과 긴밀한 협업 필요
                        - 시스템 관점의 컴포넌트 정리 역량 중요
                        """,
                "서비스 확장에 맞춰 컴포넌트 기준을 정리하는 디자인 시스템 상주 프로젝트입니다.",
                List.of("디자인", "기획"),
                List.of("Figma", "Design System", "Accessibility"));
    }

    private Project buildCommunitySearchProject(Project project) {
        return buildOutsourcingProject(
                project,
                "커뮤니티 서비스 검색/알림 기능 추가",
                "인천 연수구",
                RecruitStatus.OPEN,
                3200,
                4200,
                30,
                3,
                LocalDate.of(2026, 6, 23),
                LocalDate.of(2026, 5, 31),
                8,
                "계약 후 2주 내",
                "도급 프로젝트",
                "요구사항 정의 완료",
                "온라인",
                """
                        ※ 프로젝트 진행 방식
                        - 기존 커뮤니티 서비스에 통합 검색과 알림 기능을 추가하는 개선 프로젝트입니다.
                        - 요구사항 정의는 완료된 상태이며, 기능 단위 스프린트로 나누어 진행할 예정입니다.
                        - 주 1회 기능 리뷰 미팅과 이슈 보드 기반 협업을 생각하고 있습니다.
                        ────────────────────────

                        ※ 프로젝트의 현재 상황
                        - 서비스는 이미 운영 중이며 게시글/댓글/사용자 활동 데이터가 누적된 상태입니다.
                        - 검색 정확도와 알림 전달성이 부족해 사용자 불편이 있는 상황입니다.
                        - 신규 구축보다 기존 데이터 구조와 운영 환경을 해치지 않는 방식의 추가 개발이 중요합니다.
                        ────────────────────────

                        ※ 상세한 업무 내용
                        1. 통합 검색 기능
                        - 게시글, 댓글, 사용자, 태그 기준 검색 구조 설계
                        - 필터/정렬/추천 검색어 등 기본 검색 UX 검토
                        - Elasticsearch 기반 색인 구조 또는 유사한 검색 엔진 연동 검토

                        2. 알림 기능
                        - 댓글, 답글, 멘션, 운영 공지 알림 구조 추가
                        - 읽음/안읽음 상태 관리 및 배지 카운트 반영
                        - 푸시 또는 인앱 알림 방식 검토

                        3. 운영 및 성능
                        - 검색 응답 속도와 알림 적재/발송 성능 고려
                        - 운영자가 이슈를 확인할 수 있는 로그/모니터링 포인트 필요
                        - Redis 등 캐시 계층 활용 가능하면 좋습니다.
                        ────────────────────────

                        ※ 참고사항
                        - 기존 서비스 개선 경험이 있는 개발자를 선호합니다.
                        - 검색 품질 튜닝과 알림 상태 관리 경험이 있으면 좋습니다.
                        - 운영 데이터를 다루는 만큼 무중단 대응 경험이 있으면 우대합니다.

                        ※ 우대사항
                        - Elasticsearch 경험 보유
                        - Redis/이벤트성 처리 경험 보유
                        - 커뮤니티 서비스 운영 경험 보유
                        """,
                """
                        - 기능 단위 스프린트 운영
                        - 검색 품질 및 알림 성능 개선이 핵심
                        - 운영 중 서비스 개선 경험 우대
                        """,
                "검색 품질과 알림 성능 개선이 핵심인 운영 서비스 개선 프로젝트입니다.",
                List.of("개발", "기획"),
                List.of("Spring", "Elasticsearch", "Redis"));
    }

    private Project buildErpMaintenanceProject(Project project) {
        return buildResidentProject(
                project,
                "제조업 ERP 유지보수 백엔드 상주",
                "울산 남구",
                RecruitStatus.REVIEWING,
                550,
                180,
                1,
                LocalDate.of(2026, 6, 20),
                LocalDate.of(2026, 5, 30),
                9,
                "다음 주 미팅 예정",
                "상주 프로젝트",
                "기존 운영 문서 보유",
                "울산 오프라인",
                """
                        ※ 프로젝트 진행 방식
                        - 울산 현장 상주 중심으로 레거시 ERP 백엔드 유지보수와 개선을 맡는 프로젝트입니다.
                        - 생산/재고/구매/출고 관련 실무 담당자와 직접 소통하는 비중이 높은 편입니다.
                        - 신규 개발보다 기존 구조를 이해하고 안정적으로 개선하는 역량이 중요합니다.
                        ────────────────────────

                        ※ 프로젝트의 현재 상황
                        - 운영 중인 ERP 시스템으로, 기능 추가 요청과 기존 장애 대응이 함께 발생하고 있습니다.
                        - 운영 문서와 화면 흐름 자료는 보유하고 있으나 코드 레벨 이해와 DB 분석이 추가로 필요합니다.
                        - 특정 배치와 조회 쿼리의 성능 저하 이슈가 누적되어 있는 상태입니다.
                        ────────────────────────

                        ※ 상세한 업무 내용
                        1. 레거시 기능 유지보수
                        - 생산, 구매, 재고, 출고 관련 기능 수정
                        - 사용자 요청사항 반영 및 버그 수정
                        - 운영 중 발생하는 장애 원인 파악 및 대응

                        2. 데이터베이스 및 성능 개선
                        - MSSQL 쿼리 튜닝
                        - 배치 처리 시간 개선 검토
                        - 리포트성 조회 로직 정리

                        3. 현장 협업
                        - 실무자 요청사항 정리 및 우선순위 조율
                        - 변경 내역 문서화
                        - 운영 일정에 맞춘 배포/점검 대응
                        ────────────────────────

                        ※ 참고사항
                        - Spring MVC / MyBatis 기반 레거시 유지보수 경험이 중요합니다.
                        - 제조업 ERP 도메인 이해도가 있으면 적응이 빠릅니다.
                        - 현장 커뮤니케이션과 문서화 역량이 필요합니다.

                        ※ 우대사항
                        - MSSQL 튜닝 경험 보유
                        - ERP/그룹웨어 운영 경험 보유
                        - 레거시 시스템 인수인계 경험 보유
                        """,
                """
                        - 현장 상주 및 실무자 커뮤니케이션 비중 높음
                        - 레거시 구조 파악과 쿼리 튜닝 중요
                        - 제조업 ERP 경험 우대
                        """,
                "운영 안정성과 레거시 이해도가 중요한 제조업 ERP 상주 유지보수 프로젝트입니다.",
                List.of("개발"),
                List.of("Spring MVC", "MSSQL", "MyBatis"));
    }

    private Project buildTravelDetailProject(Project project) {
        return buildOutsourcingProject(
                project,
                "여행 플랫폼 숙소 상세 페이지 개편",
                "제주 제주시",
                RecruitStatus.OPEN,
                3800,
                4600,
                40,
                6,
                LocalDate.of(2026, 6, 27),
                LocalDate.of(2026, 5, 29),
                10,
                "계약 직후 시작",
                "도급 프로젝트",
                "스토리보드 보유",
                "온라인",
                """
                        ※ 프로젝트 진행 방식
                        - 여행 플랫폼의 숙소 상세 페이지와 결제 직전 전환 구간을 개편하는 프로젝트입니다.
                        - 계약 직후 빠르게 착수할 예정이며, 기획자/디자이너가 함께 화면 리뷰에 참여합니다.
                        - 스토리보드는 보유하고 있으나 실제 전환 흐름은 데이터 기반으로 다듬고자 합니다.
                        ────────────────────────

                        ※ 프로젝트의 현재 상황
                        - 기존 상세 페이지는 정보는 많지만 예약 CTA가 분산되어 있고, 핵심 신뢰 요소 노출이 약한 상황입니다.
                        - 예약 퍼널 이탈률이 높아 상세 페이지부터 결제 직전까지의 흐름 최적화가 필요합니다.
                        - GA4 기준 주요 이벤트는 일부 수집 중이며, A/B 테스트 가능 구조도 함께 검토하고자 합니다.
                        ────────────────────────

                        ※ 상세한 업무 내용
                        1. 상세 페이지 개편
                        - 숙소 소개, 가격, 객실 정보, 후기, 환불 규정, 위치 정보 등 핵심 정보 재배치
                        - 스티키 예약 CTA 및 주요 액션 버튼 UX 개선
                        - 모바일 중심의 예약 전환 흐름 정리

                        2. 예약 퍼널 개선
                        - 결제 직전 단계 진입 버튼/옵션 선택 흐름 개선
                        - 사용자가 이탈하는 포인트 분석 및 UI 개선안 반영
                        - 신뢰 문구, 혜택 정보, 재고/긴급성 노출 방식 검토

                        3. 측정 및 실험 기반 구조
                        - GA4 이벤트 정의/점검
                        - A/B 테스트 가능한 화면 구조 검토
                        - 운영팀이 성과를 추적할 수 있는 지표 포인트 제안
                        ────────────────────────

                        ※ 참고사항
                        - 전환 개선형 프로젝트 경험이 있으면 좋습니다.
                        - 여행/예약 서비스 UX 경험이 있으면 더 적합합니다.
                        - 퍼블리싱 품질과 데이터 기반 개선 감각을 함께 봅니다.

                        ※ 우대사항
                        - GA4 이벤트 설계 경험 보유
                        - A/B 테스트 경험 보유
                        - 예약/커머스 상세 페이지 개선 경험 보유
                        """,
                """
                        - 기획자/디자이너와 함께 리뷰 진행
                        - 전환율 개선 관점 중요
                        - GA4/A-B 테스트 경험 우대
                        """,
                "상세 페이지 전환 개선과 예약 퍼널 최적화가 목표인 여행 플랫폼 개편 프로젝트입니다.",
                List.of("개발", "디자인"),
                List.of("React", "GA4", "A/B Testing"));
    }

    private Project buildHospitalKioskProject(Project project) {
        return buildOutsourcingProject(
                project,
                "병원 예약 키오스크 화면 개발",
                "광주 서구",
                RecruitStatus.URGENT,
                2600,
                3300,
                25,
                2,
                LocalDate.of(2026, 6, 11),
                LocalDate.of(2026, 5, 28),
                11,
                "이번 주 내 착수",
                "도급 프로젝트",
                "화면 정의서 보유",
                "온라인",
                """
                        ※ 프로젝트 진행 방식
                        - 병원 현장에서 사용하는 예약/접수 키오스크 화면을 단기간에 제작하는 프로젝트입니다.
                        - 일정이 촉박하여 이번 주 내 착수를 희망하고 있으며, 피드백도 빠르게 오가는 편입니다.
                        - 화면 정의서는 보유 중이며 실제 사용성 기준의 보완 의견도 함께 받고자 합니다.
                        ────────────────────────

                        ※ 프로젝트의 현재 상황
                        - 대기 환자 접수와 예약 확인을 현장에서 빠르게 처리해야 하는 상황입니다.
                        - 고령층 사용자 비중이 높아 큰 글자, 명확한 버튼, 단순한 단계 구성이 중요합니다.
                        - 키오스크 장비 환경에 맞는 화면 비율과 터치 반응성 고려가 필요합니다.
                        ────────────────────────

                        ※ 상세한 업무 내용
                        1. 키오스크 메인 플로우 구현
                        - 예약 확인, 현장 접수, 진료과 선택, 개인정보 확인 흐름 구현
                        - 단계별 안내 메시지와 뒤로가기/취소 UX 정리
                        - 대기번호 또는 접수 완료 안내 화면 구성

                        2. 접근성 및 현장 사용성
                        - 큰 폰트, 높은 대비, 넓은 터치 영역 적용
                        - 짧고 이해하기 쉬운 문구 사용
                        - 실수 방지를 위한 확인 단계 구성

                        3. 장비 및 배포 고려사항
                        - Electron 또는 유사 실행 환경 검토 가능
                        - 현장 테스트를 고려한 빌드 산출물 제공
                        - 오류 시 재시도/관리자 호출 흐름 검토
                        ────────────────────────

                        ※ 참고사항
                        - 빠른 일정 대응이 가능해야 합니다.
                        - 접근성/고령층 UX 경험이 있으면 좋습니다.
                        - 키오스크/터치 UI 경험을 우대합니다.

                        ※ 우대사항
                        - Electron 기반 화면 개발 경험 보유
                        - 병원/공공 키오스크 프로젝트 경험 보유
                        - 접근성 UI 경험 보유
                        """,
                """
                        - 짧은 일정으로 빠른 피드백 필요
                        - 고령층 사용성 고려 필수
                        - 키오스크/터치 UI 경험 우대
                        """,
                "고령층 사용성을 고려한 병원 예약 키오스크 화면 개발 프로젝트입니다.",
                List.of("개발", "디자인"),
                List.of("React", "Electron", "Accessibility"));
    }

    private Project buildDashboardAnalystProject(Project project) {
        return buildResidentProject(
                project,
                "콘텐츠 스타트업 데이터 대시보드 분석가",
                "서울 마포구",
                RecruitStatus.OPEN,
                600,
                75,
                7,
                LocalDate.of(2026, 7, 3),
                LocalDate.of(2026, 5, 27),
                12,
                "7월 초 합류",
                "상주 프로젝트",
                "지표 정의 초안 보유",
                "홍대 오프라인",
                """
                        ※ 프로젝트 진행 방식
                        - 콘텐츠 스타트업 내부 운영 데이터를 정리하고 대시보드 체계를 만드는 분석가 포지션입니다.
                        - 홍대 오피스 기준 주 3회 현장 협업, 나머지는 원격 조율이 가능한 형태입니다.
                        - 운영팀, 마케팅팀, 제품팀과 함께 지표 정의부터 시각화까지 연결하는 역할입니다.
                        ────────────────────────

                        ※ 프로젝트의 현재 상황
                        - 서비스 데이터는 쌓이고 있으나 팀별로 보는 숫자가 달라 지표 기준이 통일되지 않은 상태입니다.
                        - 콘텐츠 소비, 리텐션, 전환, 캠페인 성과를 한눈에 볼 수 있는 대시보드가 필요합니다.
                        - 기본 지표 초안은 있으나, 실제 테이블 구조 정리와 시각화 방식 설계가 추가로 필요합니다.
                        ────────────────────────

                        ※ 상세한 업무 내용
                        1. 지표 정의 및 데이터 구조 정리
                        - 핵심 KPI 정의 및 계산 기준 합의
                        - 원천 데이터 테이블 구조 파악
                        - 중복/누락 데이터 점검 및 정리 포인트 도출

                        2. 대시보드 설계
                        - 운영 지표, 콘텐츠 성과, 사용자 흐름, 캠페인 성과 대시보드 구성
                        - 팀별로 필요한 뷰를 나누되 공통 기준은 통일
                        - Metabase 또는 유사 도구 기반 시각화 검토

                        3. 데이터 분석 및 협업
                        - SQL 기반 데이터 추출
                        - Python을 활용한 간단한 전처리/분석 가능
                        - 팀에 전달할 주간/월간 리포트 포맷 정리
                        ────────────────────────

                        ※ 참고사항
                        - 단순 리포팅보다 데이터 기준을 세우는 역할에 가깝습니다.
                        - 비개발 부서와 커뮤니케이션할 수 있는 설명력도 중요합니다.
                        - 스타트업 환경에서 변화하는 요구사항에 유연해야 합니다.

                        ※ 우대사항
                        - SQL 기반 대시보드 설계 경험 보유
                        - Metabase/BI 도구 운영 경험 보유
                        - 스타트업 데이터 분석 경험 보유
                        """,
                """
                        - 주 3회 현장 협업, 나머지 원격 조율 가능
                        - 지표 기준 수립과 대시보드 설계가 핵심
                        - SQL/BI 도구 경험 중요
                        """,
                "콘텐츠 소비 지표를 정리하고 대시보드를 시각화하는 분석가 상주 프로젝트입니다.",
                List.of("기획", "개발"),
                List.of("SQL", "Metabase", "Python"));
    }

    private Project buildOutsourcingProject(
            Project project,
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
                .client(project.getClient())
                .title(title)
                .area(area)
                .employmentType(com.github.term_project.domain.project.entity.EmploymentType.OUTSOURCING)
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
                .workDescription(workDescription.trim())
                .workMethod(workMethod.trim())
                .summary(summary)
                .categories(categories)
                .skills(skills)
                .build();
    }

    private Project buildResidentProject(
            Project project,
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
                .client(project.getClient())
                .title(title)
                .area(area)
                .employmentType(com.github.term_project.domain.project.entity.EmploymentType.RESIDENT)
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
                .workDescription(workDescription.trim())
                .workMethod(workMethod.trim())
                .summary(summary)
                .categories(categories)
                .skills(skills)
                .build();
    }
}
