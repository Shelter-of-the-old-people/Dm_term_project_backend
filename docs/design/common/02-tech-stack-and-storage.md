# 기술 스택 및 저장 전략

## 백엔드 기술
- Framework: `Spring Boot 3.x`
- Language: `Java 21`
- ORM: `Spring Data JPA`
- Validation: `spring-boot-starter-validation`
- DB: `H2 database (TCP server mode)`
- Session: `HttpSession`
- File upload: `MultipartFile`

## 현재 설정 기준
- DB URL: `jdbc:h2:tcp://localhost/~/termdb;MODE=MySQL`
- H2 콘솔 사용: `/h2-console`
- H2 서버 실행: 별도 TCP 서버 기동 후 Spring Boot 연결
- 세션 쿠키 이름: `TERMSESSION`
- 업로드 루트: `./uploads`
- 프로필 이미지 하위 디렉토리: `profile`
- 공개 URL prefix: `/files`

## 로컬 실행 순서
1. H2 TCP 서버 실행
   - `powershell -ExecutionPolicy Bypass -File .\scripts\start-h2-tcp-server.ps1`
2. Spring Boot 실행
   - `.\gradlew.bat bootRun`
3. 브라우저에서 확인
   - 서비스: `http://localhost:8080`
   - H2 콘솔: `http://localhost:8080/h2-console`

## H2 콘솔 접속값
- Saved Settings: `Generic H2 (Embedded)`
- Driver Class: `org.h2.Driver`
- JDBC URL: `jdbc:h2:tcp://localhost/~/termdb;MODE=MySQL`
- User Name: `sa`
- Password: 비워둠

## H2 콘솔 확인 포인트
- 현재 프로젝트의 주요 테이블: `users`, `projects`, `project_categories`, `project_skills`
- 실제 DB 파일 위치: `C:\Users\mhtig\termdb.mv.db`
- H2 TCP 서버가 꺼져 있으면 콘솔 접속이나 Spring Boot 기동이 실패할 수 있다.

## H2를 유지하는 이유
- 강의자료 예시와 같은 `jdbc:h2:tcp://localhost/~/...` 형식을 사용한다.
- MySQL 설치 없이도 JPA 실습과 과제 검증이 가능하다.
- 실제 데이터 파일은 사용자 홈 기준 `~/termdb.mv.db`에 유지된다.
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
