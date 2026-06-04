# 랜딩 / 프로젝트 검색 백엔드 설계

## 목적
- 랜딩 페이지에서 프로젝트 목록을 조회한다.
- 필터, 정렬, 페이지 이동 시 항상 서버에서 새 데이터를 조회하도록 지원한다.

## 요구사항 반영
- 지역검색 제외
- 참여파트 제외
- 페이지 크기 `4`
- 프론트 단 정렬 금지

## API
### `GET /api/projects`
- Query
  - `type`: `ALL | OUTSOURCING | ONSITE`
  - `sort`: `DEFAULT | LATEST | BUDGET_DESC | BUDGET_ASC | DEADLINE_ASC`
  - `page`
  - `size=4`

### 응답 항목
- `projectId`
- `title`
- `projectType`
- `recruitmentStatus`
- `techStacks`
- `budgetLabel`
- `expectedDurationDays`
- `applicantCount`
- `deadlineLabel`

## 조회 규칙
- `recruitmentStatus`는 DB 컬럼이 아니라 `deadline` 기준 계산값으로 제공한다.
- `deadlineLabel`은 `D-day`, `D-3`, `마감` 같은 문자열로 가공한다.
- `budgetLabel`은 `도급=예상비용`, `상주=월임금` 문구를 맞춰 만든다.

## 정렬 규칙
- `DEFAULT`: 생성일 내림차순과 동일하게 단순화
- `LATEST`: 생성일 내림차순
- `BUDGET_DESC`: 예산 높은순
- `BUDGET_ASC`: 예산 낮은순
- `DEADLINE_ASC`: 마감 임박순

## 상세 연동
- 목록 카드 클릭 시 `GET /api/projects/{projectId}`로 이동한다.

## 테스트 포인트
- 타입 변경 시 서버 재조회
- 정렬 변경 시 서버 재조회
- 페이지 이동 시 서버 재조회
- 전체 프로젝트 수가 10건 이상일 때 4개 단위 페이징 확인
