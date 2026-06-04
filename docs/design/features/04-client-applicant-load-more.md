# 지원자 더보기 기능 설계

## 목적
- 의뢰인 프로젝트 상세 화면에서 지원자 목록을 페이지 크기 2로 점진 조회한다.

## API
- `GET /api/client/projects/{projectId}/applicants?page={page}&size=2`

## 응답 형식
- `content`
- `page`
- `size`
- `hasNext`
- `totalElements`

## 구현 규칙
- 정렬은 `submittedAt desc`
- 프론트는 `hasNext`가 false이면 버튼을 숨기거나 비활성화한다
- 첫 페이지는 기본 2건

## 접근 제어
- 현재 로그인한 의뢰인의 프로젝트만 허용

## 테스트
- 지원자 1명: 버튼 없음
- 지원자 2명: 버튼 없음
- 지원자 3명 이상: 1회 이상 더보기 가능
