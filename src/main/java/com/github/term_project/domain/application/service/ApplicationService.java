package com.github.term_project.domain.application.service;

import com.github.term_project.domain.application.dto.ApplicationCreateResponse;
import com.github.term_project.domain.application.dto.ApplicationOnsiteLineRequest;
import com.github.term_project.domain.application.dto.ApplicationOnsiteLineResponse;
import com.github.term_project.domain.application.dto.DeveloperApplicationDetailResponse;
import com.github.term_project.domain.application.dto.DeveloperApplicationSummaryResponse;
import com.github.term_project.domain.application.dto.ProjectApplicationCreateRequest;
import com.github.term_project.domain.application.entity.Application;
import com.github.term_project.domain.application.entity.ApplicationOnsiteLine;
import com.github.term_project.domain.application.repository.ApplicationRepository;
import com.github.term_project.domain.project.entity.EmploymentType;
import com.github.term_project.domain.project.entity.Project;
import com.github.term_project.domain.project.repository.ProjectRepository;
import com.github.term_project.domain.user.entity.Role;
import com.github.term_project.domain.user.entity.User;
import com.github.term_project.domain.user.repository.UserRepository;
import com.github.term_project.global.auth.SessionUser;
import com.github.term_project.global.error.BusinessException;
import com.github.term_project.global.error.ErrorCode;
import com.github.term_project.global.util.ContactInfoValidator;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional
    public ApplicationCreateResponse createApplication(
            Long projectId,
            SessionUser sessionUser,
            ProjectApplicationCreateRequest request) {

        User developer = getDeveloper(sessionUser);
        Project project = getProject(projectId);

        validateProjectDeadline(project);

        EmploymentType requestType = EmploymentType.fromApiValue(request.employmentType());
        if (project.getEmploymentType() != requestType) {
            throw new BusinessException(ErrorCode.APPLICATION_TYPE_MISMATCH);
        }

        if (applicationRepository.existsByProject_IdAndDeveloper_Id(projectId, developer.getId())) {
            throw new BusinessException(ErrorCode.DUPLICATE_APPLICATION);
        }

        String content = normalizeRequiredText(request.content(), "content");
        if (ContactInfoValidator.containsContactInfo(content)) {
            throw new BusinessException(ErrorCode.CONTACT_INFO_INCLUDED);
        }

        Application application = requestType == EmploymentType.OUTSOURCING
                ? buildOutsourcingApplication(project, developer, content, request)
                : buildResidentApplication(project, developer, content, request);

        project.increaseApplicationCount();
        Application saved = applicationRepository.save(application);

        return new ApplicationCreateResponse(saved.getId(), project.getId(), project.getApplicationCount());
    }

    public List<DeveloperApplicationSummaryResponse> getDeveloperApplications(SessionUser sessionUser) {
        User developer = getDeveloper(sessionUser);

        return applicationRepository.findAllByDeveloper_IdOrderByCreatedAtDesc(developer.getId()).stream()
                .map(this::toSummaryResponse)
                .toList();
    }

    public DeveloperApplicationDetailResponse getDeveloperApplication(
            Long applicationId,
            SessionUser sessionUser) {
        User developer = getDeveloper(sessionUser);

        Application application = applicationRepository.findByIdAndDeveloper_Id(applicationId, developer.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_FOUND));

        return toDetailResponse(application);
    }

    private Application buildOutsourcingApplication(
            Project project,
            User developer,
            String content,
            ProjectApplicationCreateRequest request) {

        Integer workDays = requirePositive(request.workDays(), "workDays");
        Integer bidAmount = requirePositive(request.bidAmount(), "bidAmount");

        return Application.builder()
                .project(project)
                .developer(developer)
                .employmentType(EmploymentType.OUTSOURCING)
                .workDays(workDays)
                .bidAmount(bidAmount)
                .content(content)
                .onsiteLines(List.of())
                .build();
    }

    private Application buildResidentApplication(
            Project project,
            User developer,
            String content,
            ProjectApplicationCreateRequest request) {

        List<ApplicationOnsiteLine> onsiteLines = normalizeResidentLines(request);

        return Application.builder()
                .project(project)
                .developer(developer)
                .employmentType(EmploymentType.RESIDENT)
                .workDays(null)
                .bidAmount(null)
                .content(content)
                .onsiteLines(onsiteLines)
                .build();
    }

    private List<ApplicationOnsiteLine> normalizeResidentLines(ProjectApplicationCreateRequest request) {
        if (request.onsiteLines() != null && !request.onsiteLines().isEmpty()) {
            return request.onsiteLines().stream()
                    .sorted(Comparator.comparing(line -> line.sortOrder() == null ? Integer.MAX_VALUE : line.sortOrder()))
                    .map(this::toOnsiteLineEntity)
                    .toList();
        }

        if (isBlank(request.position())
                || isBlank(request.careerLevel())
                || request.headcount() == null
                || request.monthlyWage() == null) {
            throw new BusinessException(ErrorCode.ONSITE_LINE_REQUIRED);
        }

        return List.of(ApplicationOnsiteLine.builder()
                .skillCategory(request.position().trim())
                .careerLevel(request.careerLevel().trim())
                .headCount(requirePositive(request.headcount(), "headcount"))
                .monthlyPay(requirePositive(request.monthlyWage(), "monthlyWage"))
                .sortOrder(1)
                .build());
    }

    private ApplicationOnsiteLine toOnsiteLineEntity(ApplicationOnsiteLineRequest request) {
        return ApplicationOnsiteLine.builder()
                .skillCategory(normalizeRequiredText(request.position(), "onsiteLines.position"))
                .careerLevel(normalizeRequiredText(request.careerLevel(), "onsiteLines.careerLevel"))
                .headCount(requirePositive(request.headcount(), "onsiteLines.headcount"))
                .monthlyPay(requirePositive(request.monthlyWage(), "onsiteLines.monthlyWage"))
                .sortOrder(request.sortOrder() == null ? 1 : requirePositive(request.sortOrder(), "onsiteLines.sortOrder"))
                .build();
    }

    private DeveloperApplicationSummaryResponse toSummaryResponse(Application application) {
        Project project = application.getProject();

        return new DeveloperApplicationSummaryResponse(
                application.getId(),
                project.getId(),
                project.getTitle(),
                application.getEmploymentType().toApiValue(),
                application.resolveEstimateAmount(),
                project.getApplicationCount(),
                application.getWorkDays(),
                project.getExpectedDurationDays(),
                application.getCreatedAt().toLocalDate());
    }

    private DeveloperApplicationDetailResponse toDetailResponse(Application application) {
        Project project = application.getProject();
        List<ApplicationOnsiteLineResponse> onsiteLines = application.getOnsiteLines().stream()
                .map(line -> new ApplicationOnsiteLineResponse(
                        line.getSkillCategory(),
                        line.getCareerLevel(),
                        line.getHeadCount(),
                        line.getMonthlyPay(),
                        line.getSortOrder()))
                .toList();

        Integer headcount = application.getEmploymentType() == EmploymentType.OUTSOURCING
                ? 1
                : onsiteLines.stream().map(ApplicationOnsiteLineResponse::headcount).reduce(0, Integer::sum);

        return new DeveloperApplicationDetailResponse(
                application.getId(),
                project.getId(),
                project.getTitle(),
                application.getEmploymentType().toApiValue(),
                application.resolveEstimateAmount(),
                project.getApplicationCount(),
                project.getExpectedDurationDays(),
                project.getDeadline(),
                buildDeadlineLabel(project.getDeadline()),
                application.getWorkDays(),
                application.getBidAmount(),
                headcount,
                application.getContent(),
                application.getCreatedAt().toLocalDate(),
                onsiteLines);
    }

    private User getDeveloper(SessionUser sessionUser) {
        User user = userRepository.findById(sessionUser.id())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (user.getRole() != Role.DEVELOPER) {
            throw new BusinessException(ErrorCode.ROLE_MISMATCH, "개발자만 접근할 수 있습니다.");
        }
        return user;
    }

    private Project getProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "Project not found."));
    }

    private void validateProjectDeadline(Project project) {
        if (project.getDeadline().isBefore(LocalDate.now())) {
            throw new BusinessException(ErrorCode.PROJECT_ALREADY_CLOSED);
        }
    }

    private String normalizeRequiredText(String value, String fieldName) {
        if (isBlank(value)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, fieldName + " is required.");
        }
        return value.trim();
    }

    private Integer requirePositive(Integer value, String fieldName) {
        if (value == null || value < 1) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, fieldName + " must be greater than or equal to 1.");
        }
        return value;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private String buildDeadlineLabel(LocalDate deadline) {
        long days = ChronoUnit.DAYS.between(LocalDate.now(), deadline);
        if (days < 0) {
            return "마감";
        }
        return "D-" + days;
    }
}
