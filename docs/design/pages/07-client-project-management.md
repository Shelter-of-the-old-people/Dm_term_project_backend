# 의뢰인 프로젝트 관리 백엔드 설계

## 목적
- 의뢰인이 자신이 등록한 프로젝트 목록과 상세를 관리한다.
- 상세 화면에서 지원자 목록을 `더보기`로 조회한다.

## API
### `GET /api/client/projects`
- 현재 로그인한 의뢰인의 프로젝트 목록 조회

### `GET /api/client/projects/{projectId}`
- 프로젝트 요약 조회

### `GET /api/client/projects/{projectId}/applicants?page=0&size=2`
- 지원자 목록 더보기 조회

### `GET /api/client/applications/{applicationId}`
- 특정 지원서 상세 조회

## 목록 응답 항목
- `projectId`
- `title`
- `budgetLabel`
- `projectType`
- `applicantCount`
- `recruitmentDeadline`
- `deadlineLabel`

## 상세 응답 항목
- 프로젝트 요약
  - 모집 마감일
  - 예상 킥오프
  - 고용형태
  - 프로젝트 분야
  - 진행 분류
  - 기획 상태
  - 미팅 희망 지역
- 지원자 목록
  - 지원 순번
  - 지원 예상 금액 또는 월임금 요약
  - 지원일
  - 상세보기 버튼용 `applicationId`

## 접근 제어
- 프로젝트 작성자 본인만 조회 가능
- 다른 의뢰인의 프로젝트/지원서 접근 차단

## 테스트 포인트
- 프로젝트별 지원자 수 일치
- 더보기 size=2 동작
- 마지막 페이지 이후 `hasNext=false`
- 지원서 상세 조회 가능
