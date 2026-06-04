# 개발자 지원 목록 / 지원서 열람 백엔드 설계

## 목적
- 개발자가 자신이 지원한 프로젝트 목록을 확인한다.
- 각 항목 상세에서 본인이 제출한 지원서 내용을 열람한다.

## API
### `GET /api/developer/applications`
- 현재 개발자의 전체 지원 목록 조회

### `GET /api/developer/applications/{applicationId}`
- 특정 지원서 상세 조회
- 프로젝트 요약과 제출한 지원서 내용을 함께 반환

## 목록 응답 항목
- `applicationId`
- `projectId`
- `projectTitle`
- `budgetLabel`
- `applicantCount`
- `expectedDurationDays`
- `appliedAt`

## 상세 응답 항목
- 프로젝트 요약
  - 제목
  - 고용형태
  - 예산
  - 기간
  - 지원자 수
  - 마감 정보
- 지원서 본문
  - 도급: 작업기간, 지원금액, 지원내용
  - 상주: 포지션 라인 목록, 지원내용

## 구현 메모
- 상태 탭(`지원/미팅/...`)은 구현하지 않는다.
- 지원서 상세는 본인 것만 조회 가능해야 한다.

## 테스트 포인트
- 지원 후 목록에 즉시 반영
- 목록의 지원자 수가 프로젝트 현재 값과 동일
- 다른 개발자의 applicationId 조회 차단
