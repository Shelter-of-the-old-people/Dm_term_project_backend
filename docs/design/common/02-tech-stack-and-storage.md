# 기술 스택 및 저장 전략

## 백엔드 기술
- Framework: `Spring Boot 3.x`
- Language: `Java 21`
- ORM: `Spring Data JPA`
- Validation: `spring-boot-starter-validation`
- DB: `H2 file database`
- Session: `HttpSession`
- File upload: `MultipartFile`

## 현재 설정 기준
- DB URL: `jdbc:h2:file:./data/termdb;MODE=MySQL;AUTO_SERVER=TRUE`
- H2 콘솔 사용: `/h2-console`
- 세션 쿠키 이름: `TERMSESSION`
- 업로드 루트: `./uploads`
- 프로필 이미지 하위 디렉토리: `profile`
- 공개 URL prefix: `/files`

## H2를 유지하는 이유
- 현재 프로젝트가 이미 H2 file DB 기준으로 잡혀 있다.
- 검사 시 외부 DB 설치 없이 바로 실행할 수 있다.
- 애플리케이션 재시작 후에도 데이터가 유지된다.
- H2 콘솔로 데이터 검증이 쉽다.

## 저장 전략
### 사용자
- `users` 테이블에 로그인 정보와 역할을 저장한다.

### 개발자 프로필
- 프로필 핵심 정보는 `developer_profiles`에 저장한다.
- 검색 태그는 다건 저장이므로 별도 테이블로 분리한다.

### 프로젝트
- 프로젝트 기본 정보는 `projects`에 저장한다.
- 프로젝트 분야와 기술 스택은 다건 저장이므로 별도 테이블로 분리한다.

### 지원서
- 공통 지원서 정보는 `applications`에 저장한다.
- 상주 지원서의 다중 포지션 행은 `application_onsite_lines`에 저장한다.

### 파일
- 이미지 파일은 `uploads/profile`에 저장한다.
- DB에는 파일 자체가 아니라 공개 경로 문자열만 저장한다.

## 세션 전략
- 로그인 성공 시 `SessionUser`를 세션에 저장한다.
- 인증이 필요한 API는 `@LoginUser` 기반으로 사용자 식별을 받는다.
- 역할 검증은 컨트롤러/서비스 단에서 명시적으로 수행한다.

## 프론트 연동 규칙
- 프론트는 쿠키 기반 호출을 위해 항상 `credentials: include`로 요청한다.
- 이미지 URL은 서버가 반환한 `/files/profile/...` 경로를 그대로 사용한다.
