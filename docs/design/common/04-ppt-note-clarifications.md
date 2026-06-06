# PPT Memo Clarifications

## Purpose
- This note captures the requirement clarifications confirmed from the latest PPT file and its speaker notes.
- Use this file as the final interpretation baseline when slide text, screenshots, and real Freemoa behavior conflict.

## Confirmed Interpretations
- The PPT's `landing page` means the `project search / project list page`, not the real Freemoa marketing home.
- Therefore the first evaluation screen should be the project page, and both developer and client users enter from that same screen.
- The old marketing-style home can remain only as a reference or secondary route.

- Filtering, sorting, and pagination on the landing/project page must all trigger server requests.
- Client-side array sorting is not acceptable for the evaluated project list.
- Landing project list page size is fixed to `4`.

- Developer profile image upload must follow:
  `multipart/form-data -> file saved on server filesystem -> DB stores only file path or URL string`
- The DB must not store the image binary itself.
- In local development, the current backend config means:
  - physical file location: `./uploads/profile/...`
  - DB value / returned path: `/files/profile/...`

- Developer mypage only needs:
  - project management
  - profile management

- Project detail only needs the summary view.
- Tab UI is optional.

- Client project detail must support applicant list `load more`.
- Page size for that list is fixed to `2`.
- When no more applicants remain, the `load more` button should be hidden or disabled.

## Slide Conflict Resolution
- Slides 13 and 14 conflict with each other in the latest PPT.
- The safe interpretation remains the same as before, based on the actual Freemoa form structure:
  - outsourcing / 도급: `작업기간`, `지원 금액`, `지원 내용`
  - resident / 상주: `기술구분`, `연차구분`, `인원수`, `임금`, `지원 내용`

## Notes Worth Preserving
- Login is enough for evaluation; signup is optional convenience only.
- Contact information detection in application content is mandatory.
- The applicant count should visibly increase after applying.
- Dummy project data should be prepared with at least `10` projects.
