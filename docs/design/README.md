# 백엔드 설계 문서 인덱스

## 목적
- 본 문서는 `Dm_term_project_backend` 레포의 구현 기준을 고정하기 위한 백엔드 설계 문서 모음이다.
- 발표자료, 실제 프리모아 공개 화면, 현재 Spring Boot/H2 기반 프로젝트 구조를 함께 반영한다.
- 기능 번호 중복, 슬라이드 이미지 오첨부 같은 자료 불일치는 이미 해석을 끝낸 상태로 본다.

## 공통 문서
- [요구사항 기준선](./common/01-requirement-baseline.md)
- [기술 스택 및 저장 전략](./common/02-tech-stack-and-storage.md)
- [ERD 및 API 규칙](./common/03-erd-and-api-conventions.md)

## 페이지 기준 문서
- [인증 및 세션](./pages/01-auth-and-session.md)
- [랜딩 / 프로젝트 검색](./pages/02-landing-project-search.md)
- [개발자 프로필 관리](./pages/03-developer-profile-management.md)
- [개발자 지원 목록 / 지원서 열람](./pages/04-developer-application-history.md)
- [프로젝트 상세 / 프로젝트 지원](./pages/05-project-detail-and-application.md)
- [의뢰인 프로젝트 생성](./pages/06-client-project-create.md)
- [의뢰인 프로젝트 관리](./pages/07-client-project-management.md)

## 기능 기준 문서
- [프로필 이미지 업로드](./features/01-profile-image-upload.md)
- [프로젝트 검색 / 정렬 / 페이징](./features/02-project-search-filter-sort-pagination.md)
- [지원 내용 연락처 차단](./features/03-contact-info-validation.md)
- [지원자 더보기](./features/04-client-applicant-load-more.md)
- [더미 데이터 시딩](./features/05-dummy-data-seeding.md)

## 문서 작성 원칙
- 모든 API는 세션 쿠키 기반으로 동작한다.
- 응답은 공통 `ApiResponse`, 목록은 `PageResponse` 형식을 따른다.
- 이미지 파일은 DB에 저장하지 않고 서버 로컬 디렉토리에 저장한다.
- 페이지 문서는 사용자 흐름 기준, 기능 문서는 공통 구현 규칙 기준으로 나눈다.
