# 개발자 프로필 관리 백엔드 설계

## 목적
- 개발자 마이페이지에서 프로필을 조회/수정한다.
- 프로필 이미지와 검색 태그 규칙을 함께 처리한다.

## 저장 대상
- 프로필 이미지 경로
- 지원분야(복수)
- 활동가능여부
- 상주가능여부
- 지역 2단계
- 형태
- 경력연차
- 검색태그
- 소개글

## API
### `GET /api/developer/profile`
- 현재 로그인한 개발자 프로필 조회

### `PUT /api/developer/profile`
- 프로필 텍스트/선택값 수정
- 이미지 업로드는 별도 API로 분리

## 제안 Request DTO
- `supportFields: string[]`
- `activeAvailable: boolean`
- `onsiteAvailable: boolean`
- `regionSido: string`
- `regionSigungu: string`
- `businessType: string`
- `careerYears: int`
- `searchTags: string[]`
- `introduction: string`

## 검증 규칙
- `searchTags` 최대 5개
- 태그 중복 제거
- 지역은 두 값 모두 있어야 저장 가능
- 개발자 역할 사용자만 접근 가능

## 저장 전략
- `supportFields`는 문자열 컬렉션 테이블 또는 enum 컬렉션으로 저장
- `searchTags`는 별도 child table 저장
- 소개글은 `TEXT` 수준 길이 허용

## 연관 기능
- 이미지 파일 업로드는 [프로필 이미지 업로드](../features/01-profile-image-upload.md) 문서 기준

## 테스트 포인트
- 태그 5개 저장 성공
- 태그 6개 저장 실패
- 태그 수정 시 기존 태그 교체
- 개발자 아닌 사용자의 접근 차단
