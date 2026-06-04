# 프로젝트 상세 / 프로젝트 지원 백엔드 설계

## 목적
- 프로젝트 요약 상세를 제공한다.
- 개발자가 도급 또는 상주 형식으로 지원서를 제출한다.

## 조회 API
### `GET /api/projects/{projectId}`
- 요약 탭에 필요한 정보만 반환
- 상세 업무내용, 포트폴리오, 과업지시서 같은 제외 항목은 반환하지 않는다.

## 지원 API
### `POST /api/projects/{projectId}/applications`
- 로그인한 개발자만 접근 가능
- 프로젝트 유형에 따라 Request 형식이 달라진다.

## 도급 지원 Request
- `applicationType: OUTSOURCING`
- `workingDays`
- `bidAmount`
- `proposalText`

## 상주 지원 Request
- `applicationType: ONSITE`
- `proposalText`
- `onsiteLines`
  - `skillCategory`
  - `careerLevel`
  - `headCount`
  - `monthlyPay`
  - `sortOrder`

## 핵심 검증
- 지원 내용에 이메일/전화번호 포함 금지
- 이미 지원한 프로젝트에 중복 지원 금지
- 프로젝트 마감 후 지원 금지
- 개발자 역할만 지원 가능
- 프로젝트 유형과 Request 유형이 일치해야 한다
- 상주 지원은 `onsiteLines` 최소 1개 필요

## 후처리
- 지원서 저장 후 프로젝트 `applicantCount` 증가
- 개발자 지원 목록 조회에 노출 가능해야 함
- 의뢰인 프로젝트 상세 지원자 목록에도 반영

## 테스트 포인트
- 도급 지원 성공
- 상주 지원 성공
- 연락처 포함 지원 실패
- 마감 프로젝트 지원 실패
- 중복 지원 실패
