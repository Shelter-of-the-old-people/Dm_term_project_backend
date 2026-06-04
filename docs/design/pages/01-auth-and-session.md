# 인증 및 세션 페이지 지원 설계

## 목적
- 테스트와 시연을 위해 간단한 로그인 흐름을 제공한다.
- 회원가입은 평가 필수는 아니지만 계정 초기화 편의를 위해 최소 구현을 허용한다.

## 대상 화면
- 로그인 페이지
- 회원가입 페이지(선택)
- 상단 사용자 정보 확인

## 백엔드 책임
- 로그인 API 제공
- 회원가입 API 제공(선택)
- 현재 세션 사용자 조회 API 제공
- 로그아웃 API 제공

## API 설계
### `POST /api/auth/login`
- Request: `loginId`, `password`
- Success: 세션 생성, 사용자 역할/닉네임 반환
- Failure: 인증 실패 메시지 반환

### `POST /api/auth/signup`
- Request: `loginId`, `password`, `nickname`, `role`
- Validation:
  - `loginId` 중복 불가
  - `role`은 `DEVELOPER`, `CLIENT`만 허용

### `GET /api/auth/me`
- 로그인 상태 확인용
- 프론트의 초기 라우팅/가드에서 사용

### `POST /api/auth/logout`
- 세션 무효화

## 세션 처리 규칙
- 세션 키는 `SessionConst.LOGIN_USER`
- 세션 값은 `SessionUser`
- 인증이 필요한 컨트롤러는 `@LoginUser(required = true)` 사용

## 예외 처리
- 로그인 실패: `INVALID_CREDENTIALS`
- 세션 없음: `UNAUTHORIZED`
- 권한 없음: `FORBIDDEN`

## 테스트 포인트
- 개발자 계정 로그인
- 의뢰인 계정 로그인
- 로그아웃 후 보호 API 접근 차단
- 다른 역할의 마이페이지 API 호출 시 차단
