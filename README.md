# Freemoa Term Project Backend

Spring Boot 기반 프리모아 클론코딩 텀프로젝트 백엔드입니다.

## 기술 스택
- Java 21
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- H2 Database
- HttpSession

## 로컬 실행 방법
1. H2 TCP 서버 실행

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\start-h2-tcp-server.ps1
```

2. Spring Boot 실행

```powershell
.\gradlew.bat bootRun
```

3. 브라우저에서 확인
- 서비스: `http://localhost:8080`
- H2 콘솔: `http://localhost:8080/h2-console`

## H2 콘솔 접속 정보
- Driver Class: `org.h2.Driver`
- JDBC URL: `jdbc:h2:tcp://localhost/~/termdb;MODE=MySQL`
- User Name: `sa`
- Password: 빈칸

실제 DB 파일은 `C:\Users\mhtig\termdb.mv.db`에 생성됩니다.

## 테스트 계정
- 개발자: `developer1 / 1234`
- 의뢰인: `client1 / 1234`

## 더미 데이터 기준
- 사용자 2명 시드 데이터 자동 생성
- 프로젝트 12건 시드 데이터 자동 생성
- 도급 / 상주 프로젝트 혼합 구성

## 빠른 검증 포인트
- `GET /api/projects?page=1&size=1` 응답 확인
- `GET /api/session` 비로그인 시 `401` 확인
- H2 콘솔에서 `users`, `projects`, `project_categories`, `project_skills` 테이블 확인

## 참고 문서
- [백엔드 설계 문서](./docs/design/README.md)
- [기술 스택 및 저장 전략](./docs/design/common/02-tech-stack-and-storage.md)
- [더미 데이터 시딩 설계](./docs/design/features/05-dummy-data-seeding.md)

## 주의 사항
- H2 TCP 서버가 실행되지 않으면 Spring Boot가 DB에 연결할 수 없습니다.
- `9092` 포트를 이미 다른 H2 서버가 사용 중이면 기존 프로세스를 정리한 뒤 다시 실행해야 합니다.
