package com.github.term_project.domain.project.service;

import com.github.term_project.domain.application.dto.ClientApplicantSummaryResponse;
import com.github.term_project.domain.application.dto.ClientApplicationDetailResponse;
import com.github.term_project.domain.application.dto.ClientApplicationOnsiteLineResponse;
import com.github.term_project.domain.application.entity.Application;
import com.github.term_project.domain.application.repository.ApplicationRepository;
import com.github.term_project.domain.project.dto.ClientProjectCreateRequest;
import com.github.term_project.domain.project.dto.ClientProjectCreateResponse;
import com.github.term_project.domain.project.dto.ClientProjectDetailResponse;
import com.github.term_project.domain.project.dto.ClientProjectSummaryResponse;
import com.github.term_project.domain.project.entity.EmploymentType;
import com.github.term_project.domain.project.entity.Project;
import com.github.term_project.domain.project.entity.RecruitStatus;
import com.github.term_project.domain.project.repository.ProjectRepository;
import com.github.term_project.domain.user.entity.Role;
import com.github.term_project.domain.user.entity.User;
import com.github.term_project.domain.user.repository.UserRepository;
import com.github.term_project.global.auth.SessionUser;
import com.github.term_project.global.common.PageResponse;
import com.github.term_project.global.error.BusinessException;
import com.github.term_project.global.error.ErrorCode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientProjectService {

    private static final int CLIENT_APPLICANT_PAGE_SIZE = 2;

    private final ProjectRepository projectRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    @Transactional
    public ClientProjectCreateResponse createProject(
            SessionUser sessionUser,
            ClientProjectCreateRequest request) {

        User client = getClient(sessionUser);
        EmploymentType employmentType = EmploymentType.fromApiValue(request.projectType());
        List<String> categories = normalizeTextList(request.projectFields(), "projectFields");
        List<String> skills = normalizeTextList(request.techStacks(), "techStacks");
        LocalDate deadline = requireFutureDate(request.recruitmentDeadline(), "recruitmentDeadline");
        int budgetAmount = requirePositive(request.budgetAmount(), "budgetAmount");
        int expectedDurationDays = requirePositive(request.expectedDurationDays(), "expectedDurationDays");
        String meetingRegion = normalizeRequiredText(request.meetingRegion(), "meetingRegion");
        String progressMethod = normalizeRequiredText(request.progressMethod(), "progressMethod");
        String workDescription = normalizeRequiredText(request.workDescription(), "workDescription");

        Project project = Project.builder()
                .client(client)
                .title(normalizeRequiredText(request.title(), "title"))
                .area(meetingRegion)
                .employmentType(employmentType)
                .recruitStatus(RecruitStatus.OPEN)
                .budgetMin(employmentType == EmploymentType.OUTSOURCING ? budgetAmount : null)
                .budgetMax(employmentType == EmploymentType.OUTSOURCING ? budgetAmount : null)
                .monthlyWage(employmentType == EmploymentType.RESIDENT ? budgetAmount : null)
                .expectedDurationDays(expectedDurationDays)
                .applicationCount(0)
                .deadline(deadline)
                .postedAt(LocalDate.now())
                .displayOrder((int) projectRepository.count() + 1)
                .kickoffSchedule(resolveKickoffSchedule(request.kickoffSchedule()))
                .progressType(resolveProgressType(employmentType))
                .planningStatus(normalizeRequiredText(request.planningStatus(), "planningStatus"))
                .meetingLocation(meetingRegion)
                .workDescription(workDescription)
                .workMethod(progressMethod)
                .summary(buildSummary(workDescription))
                .categories(categories)
                .skills(skills)
                .build();

        Project saved = projectRepository.save(project);
        return new ClientProjectCreateResponse(saved.getId());
    }

    public List<ClientProjectSummaryResponse> getClientProjects(SessionUser sessionUser) {
        User client = getClient(sessionUser);

        return projectRepository.findAllByClient_IdOrderByPostedAtDescIdDesc(client.getId()).stream()
                .map(this::toClientProjectSummary)
                .toList();
    }

    public ClientProjectDetailResponse getClientProject(Long projectId, SessionUser sessionUser) {
        User client = getClient(sessionUser);
        Project project = getOwnedProject(projectId, client.getId());

        return new ClientProjectDetailResponse(
                project.getId(),
                project.getTitle(),
                project.getDeadline(),
                buildDeadlineLabel(project.getDeadline()),
                project.getKickoffSchedule(),
                project.getEmploymentType().toApiValue(),
                List.copyOf(project.getCategories()),
                project.getProgressType(),
                project.getPlanningStatus(),
                project.getMeetingLocation(),
                project.getWorkDescription(),
                project.getWorkMethod(),
                project.getApplicationCount());
    }

    public PageResponse<ClientApplicantSummaryResponse> getProjectApplicants(
            Long projectId,
            SessionUser sessionUser,
            int page) {

        User client = getClient(sessionUser);
        getOwnedProject(projectId, client.getId());
        validateApplicantPage(page);

        List<Application> applications = applicationRepository.findAllByProject_IdOrderByCreatedAtDesc(projectId);
        int fromIndex = Math.min((page - 1) * CLIENT_APPLICANT_PAGE_SIZE, applications.size());
        int toIndex = Math.min(fromIndex + CLIENT_APPLICANT_PAGE_SIZE, applications.size());

        List<ClientApplicantSummaryResponse> items = applications.subList(fromIndex, toIndex).stream()
                .map(application -> new ClientApplicantSummaryResponse(
                        application.getId(),
                        application.getDeveloper().getId(),
                        application.getDeveloper().getNickname(),
                        application.getEmploymentType().toApiValue(),
                        application.resolveEstimateAmount(),
                        application.getCreatedAt().toLocalDate()))
                .toList();

        return PageResponse.of(items, page, CLIENT_APPLICANT_PAGE_SIZE, applications.size());
    }

    public ClientApplicationDetailResponse getClientApplication(Long applicationId, SessionUser sessionUser) {
        User client = getClient(sessionUser);

        Application application = applicationRepository.findByIdAndProject_Client_Id(applicationId, client.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_FOUND));

        List<ClientApplicationOnsiteLineResponse> onsiteLines = application.getOnsiteLines().stream()
                .map(line -> new ClientApplicationOnsiteLineResponse(
                        line.getSkillCategory(),
                        line.getCareerLevel(),
                        line.getHeadCount(),
                        line.getMonthlyPay(),
                        line.getSortOrder()))
                .toList();

        int headcount = application.getEmploymentType() == EmploymentType.OUTSOURCING
                ? 1
                : onsiteLines.stream().map(ClientApplicationOnsiteLineResponse::headcount).reduce(0, Integer::sum);

        return new ClientApplicationDetailResponse(
                application.getId(),
                application.getProject().getId(),
                application.getDeveloper().getId(),
                application.getDeveloper().getNickname(),
                application.getEmploymentType().toApiValue(),
                application.getWorkDays(),
                application.getBidAmount(),
                headcount,
                application.getContent(),
                application.getCreatedAt().toLocalDate(),
                onsiteLines);
    }

    private ClientProjectSummaryResponse toClientProjectSummary(Project project) {
        return new ClientProjectSummaryResponse(
                project.getId(),
                project.getTitle(),
                project.getEmploymentType().toApiValue(),
                project.getBudgetMin(),
                project.getBudgetMax(),
                project.getMonthlyWage(),
                project.getApplicationCount(),
                project.getDeadline(),
                buildDeadlineLabel(project.getDeadline()));
    }

    private Project getOwnedProject(Long projectId, Long clientId) {
        return projectRepository.findByIdAndClient_Id(projectId, clientId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "존재하지 않는 의뢰 프로젝트입니다."));
    }

    private User getClient(SessionUser sessionUser) {
        User user = userRepository.findById(sessionUser.id())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (user.getRole() != Role.CLIENT) {
            throw new BusinessException(ErrorCode.ROLE_MISMATCH, "의뢰인만 접근할 수 있습니다.");
        }
        return user;
    }

    private void validateApplicantPage(int page) {
        if (page < 1) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, "page must be greater than or equal to 1.");
        }
    }

    private String buildSummary(String workDescription) {
        if (workDescription.length() <= 120) {
            return workDescription;
        }
        return workDescription.substring(0, 117) + "...";
    }

    private String resolveKickoffSchedule(String kickoffSchedule) {
        if (kickoffSchedule == null || kickoffSchedule.isBlank()) {
            return "미팅 후";
        }
        return kickoffSchedule.trim();
    }

    private String resolveProgressType(EmploymentType employmentType) {
        return employmentType == EmploymentType.OUTSOURCING ? "도급 프로젝트" : "상주 프로젝트";
    }

    private String normalizeRequiredText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, fieldName + " is required.");
        }
        return value.trim();
    }

    private List<String> normalizeTextList(List<String> values, String fieldName) {
        if (values == null || values.isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, fieldName + " is required.");
        }

        Set<String> normalized = new LinkedHashSet<>();
        for (String value : values) {
            if (value == null || value.isBlank()) {
                continue;
            }
            normalized.add(value.trim());
        }

        if (normalized.isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, fieldName + " is required.");
        }
        return List.copyOf(normalized);
    }

    private int requirePositive(Integer value, String fieldName) {
        if (value == null || value < 1) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, fieldName + " must be greater than or equal to 1.");
        }
        return value;
    }

    private LocalDate requireFutureDate(LocalDate value, String fieldName) {
        if (value == null) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, fieldName + " is required.");
        }
        if (!value.isAfter(LocalDate.now())) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, fieldName + " must be after today.");
        }
        return value;
    }

    private String buildDeadlineLabel(LocalDate deadline) {
        long days = ChronoUnit.DAYS.between(LocalDate.now(), deadline);
        if (days < 0) {
            return "마감";
        }
        if (days == 0) {
            return "D-day";
        }
        return "D-" + days;
    }
}
