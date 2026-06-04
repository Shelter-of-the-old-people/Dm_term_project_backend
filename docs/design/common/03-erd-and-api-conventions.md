# ERD 및 API 규칙

## 핵심 엔티티

### 1. User
- `id`
- `loginId`
- `password`
- `nickname`
- `role`
- `createdAt`, `updatedAt`

### 2. DeveloperProfile
- `id`
- `userId`
- `profileImagePath`
- `activeAvailable`
- `onsiteAvailable`
- `regionSido`
- `regionSigungu`
- `businessType`
- `careerYears`
- `introduction`

### 3. DeveloperProfileTag
- `id`
- `profileId`
- `tagName`

### 4. Project
- `id`
- `clientId`
- `title`
- `recruitmentDeadline`
- `projectType` (`OUTSOURCING`, `ONSITE`)
- `budgetAmount`
- `expectedDurationDays`
- `planningStatus`
- `meetingRegion`
- `workDescription`
- `progressMethod`
- `applicantCount`
- `createdAt`, `updatedAt`

### 5. ProjectField
- `id`
- `projectId`
- `fieldType` (`DEVELOPMENT`, `DESIGN`, `PLANNING`)

### 6. ProjectTechStack
- `id`
- `projectId`
- `techName`

### 7. Application
- `id`
- `projectId`
- `developerId`
- `applicationType` (`OUTSOURCING`, `ONSITE`)
- `proposalText`
- `workingDays`
- `bidAmount`
- `submittedAt`

### 8. ApplicationOnsiteLine
- `id`
- `applicationId`
- `skillCategory`
- `careerLevel`
- `headCount`
- `monthlyPay`
- `sortOrder`

## API 명명 규칙
- 공용 조회: `/api/projects/...`
- 인증: `/api/auth/...`
- 개발자 마이페이지: `/api/developer/...`
- 의뢰인 마이페이지: `/api/client/...`

## 공통 응답 규칙
- 성공 응답: `ApiResponse.success(data)`
- 실패 응답: `ApiResponse.fail(code, message)`
- 목록 응답: `PageResponse<T>`

## 제안 API 목록
- `POST /api/auth/login`
- `POST /api/auth/signup`
- `POST /api/auth/logout`
- `GET /api/auth/me`
- `GET /api/projects`
- `GET /api/projects/{projectId}`
- `POST /api/projects/{projectId}/applications`
- `GET /api/developer/profile`
- `PUT /api/developer/profile`
- `POST /api/developer/profile/image`
- `GET /api/developer/applications`
- `GET /api/developer/applications/{applicationId}`
- `POST /api/client/projects`
- `GET /api/client/projects`
- `GET /api/client/projects/{projectId}`
- `GET /api/client/projects/{projectId}/applicants`
- `GET /api/client/applications/{applicationId}`

## 검증 규칙
- 역할 불일치 시 `403`
- 세션 없음 시 `401`
- 잘못된 요청 데이터는 `400`
- 존재하지 않는 자원은 `404`
- 지원 내용에 연락처가 포함되면 `400`
- 검색 태그는 `최대 5개`
- 프로필 이미지는 `image/*` MIME만 허용
