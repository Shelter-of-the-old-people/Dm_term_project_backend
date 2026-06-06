# Backend Design Docs

## Purpose
- This folder keeps the backend-side design baseline for the Freemoa term project.
- The documents combine the PPT requirements, the latest speaker-note clarifications, and the current Spring Boot + H2 implementation direction.

## Core Documents
- [01 Requirement Baseline](./common/01-requirement-baseline.md)
- [02 Tech Stack And Storage](./common/02-tech-stack-and-storage.md)
- [03 ERD And API Conventions](./common/03-erd-and-api-conventions.md)
- [04 PPT Note Clarifications](./common/04-ppt-note-clarifications.md)

## Page Documents
- [01 Auth And Session](./pages/01-auth-and-session.md)
- [02 Landing Project Search](./pages/02-landing-project-search.md)
- [03 Developer Profile Management](./pages/03-developer-profile-management.md)
- [04 Developer Application History](./pages/04-developer-application-history.md)
- [05 Project Detail And Application](./pages/05-project-detail-and-application.md)
- [06 Client Project Create](./pages/06-client-project-create.md)
- [07 Client Project Management](./pages/07-client-project-management.md)

## Feature Documents
- [01 Profile Image Upload](./features/01-profile-image-upload.md)
- [02 Project Search Filter Sort Pagination](./features/02-project-search-filter-sort-pagination.md)
- [03 Contact Info Validation](./features/03-contact-info-validation.md)
- [04 Client Applicant Load More](./features/04-client-applicant-load-more.md)
- [05 Dummy Data Seeding](./features/05-dummy-data-seeding.md)

## Current Baseline
- The evaluated landing page means the `project search page`, not the real Freemoa marketing home.
- Authentication remains `session-based`.
- Uploaded profile image files are stored on the backend filesystem, while the database stores only the returned path string.
- Project list filtering, sorting, and pagination must all be backed by server requests.
- The PPT conflict around slides 13 and 14 is resolved by following the real Freemoa form split:
  - outsourcing / 도급: period, amount, content
  - resident / 상주: skill type, career level, headcount, wage, content
