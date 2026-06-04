# 프로필 이미지 업로드 기능 설계

## 목적
- 개발자 프로필 이미지를 서버에 업로드하고 공개 경로를 저장한다.

## 업로드 흐름
1. 프론트가 이미지 파일 선택
2. `multipart/form-data`로 업로드 API 호출
3. 서버가 MIME 검증
4. 서버 로컬 디렉토리에 저장
5. 공개 경로 문자열 반환
6. 프론트가 반환 경로를 프로필 저장 API에 반영하거나 즉시 화면 반영

## API
### `POST /api/developer/profile/image`
- Part name: `file`
- Response: `imageUrl`

## 저장 규칙
- 저장 경로: `./uploads/profile/{uuid}.{ext}`
- DB 저장 값: `/files/profile/{uuid}.{ext}`
- 원본 파일명은 로그 용도로만 사용

## 검증
- 빈 파일 금지
- `image/*` MIME만 허용
- 최대 파일 크기는 multipart 설정에 의존

## 예외
- 잘못된 파일 타입
- 저장 실패
- 인증되지 않은 접근

## 검사 포인트
- DB에 파일 경로 저장 여부
- 서버 로컬 디렉토리에 실제 파일 존재 여부
- 클라이언트 코드에 정적 프로필 이미지 하드코딩 금지
