# 의뢰인 프로젝트 생성 백엔드 설계

## 목적
- 의뢰인이 새로운 프로젝트를 등록한다.

## API
### `POST /api/client/projects`
- 의뢰인 역할만 접근 가능

## Request DTO
- `title`
- `recruitmentDeadline`
- `projectType`
- `budgetAmount`
- `expectedDurationDays`
- `projectFields`
- `planningStatus`
- `meetingRegion`
- `workDescription`
- `progressMethod`
- `techStacks`

## 저장 규칙
- `projectFields`는 복수 선택 저장
- `techStacks`는 문자열 리스트 저장
- `applicantCount`는 0으로 초기화
- 작성자는 현재 로그인한 의뢰인 계정으로 고정

## 검증 규칙
- 마감일은 오늘 이후
- 예산은 양수
- 프로젝트 분야 최소 1개
- 기술 스택 최소 1개 권장, 필요 시 필수 처리
- 프로젝트 유형은 `OUTSOURCING`, `ONSITE` 중 하나

## 제외 항목
- 프로젝트 현재 상황
- 예산 및 기간 중복 설명
- 상세한 업무내용 추가 섹션
- 참고사항

## 테스트 포인트
- 도급 프로젝트 생성
- 상주 프로젝트 생성
- 생성 후 랜딩 목록 조회 가능
- 생성 후 의뢰인 프로젝트 관리 목록 조회 가능
