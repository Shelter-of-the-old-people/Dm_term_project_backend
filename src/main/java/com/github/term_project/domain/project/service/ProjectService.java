package com.github.term_project.domain.project.service;

import com.github.term_project.domain.project.dto.ProjectDetailResponse;
import com.github.term_project.domain.project.dto.ProjectListItemResponse;
import com.github.term_project.domain.project.dto.ProjectSortType;
import com.github.term_project.domain.project.dto.ProjectTypeFilter;
import com.github.term_project.domain.project.entity.EmploymentType;
import com.github.term_project.domain.project.entity.Project;
import com.github.term_project.domain.project.repository.ProjectRepository;
import com.github.term_project.global.common.PageResponse;
import com.github.term_project.global.error.BusinessException;
import com.github.term_project.global.error.ErrorCode;
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
public class ProjectService {

    private final ProjectRepository projectRepository;

    public PageResponse<ProjectListItemResponse> getProjects(String type, String sort, int page, int size) {
        validatePageParameters(page, size);

        ProjectTypeFilter typeFilter = ProjectTypeFilter.from(type);
        ProjectSortType sortType = ProjectSortType.from(sort);

        List<Project> filtered = projectRepository.findAll().stream()
                .filter(project -> typeFilter == ProjectTypeFilter.ALL
                        || project.getEmploymentType() == typeFilter.employmentType())
                .sorted(resolveComparator(sortType))
                .toList();

        int fromIndex = Math.min((page - 1) * size, filtered.size());
        int toIndex = Math.min(fromIndex + size, filtered.size());

        List<ProjectListItemResponse> items = filtered.subList(fromIndex, toIndex).stream()
                .map(this::toListItemResponse)
                .toList();

        return PageResponse.of(items, page, size, filtered.size());
    }

    public ProjectDetailResponse getProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "Project not found."));
        return toDetailResponse(project);
    }

    private void validatePageParameters(int page, int size) {
        if (page < 1) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, "page must be greater than or equal to 1.");
        }
        if (size < 1) {
            throw new BusinessException(ErrorCode.INVALID_INPUT, "size must be greater than or equal to 1.");
        }
    }

    private Comparator<Project> resolveComparator(ProjectSortType sortType) {
        return switch (sortType) {
            case FREEMOA -> Comparator.comparing(Project::getDisplayOrder);
            case LATEST -> Comparator.comparing(Project::getPostedAt).reversed()
                    .thenComparing(Project::getDisplayOrder);
            case HIGH_BUDGET -> Comparator.comparingInt(this::resolveSortBudget).reversed()
                    .thenComparing(Project::getDisplayOrder);
            case LOW_BUDGET -> Comparator.comparingInt(this::resolveSortBudget)
                    .thenComparing(Project::getDisplayOrder);
            case DEADLINE -> Comparator.comparing(Project::getDeadline)
                    .thenComparing(Project::getDisplayOrder);
        };
    }

    private int resolveSortBudget(Project project) {
        if (project.getEmploymentType() == EmploymentType.RESIDENT) {
            return project.getMonthlyWage() == null ? 0 : project.getMonthlyWage();
        }
        int budgetMax = project.getBudgetMax() == null ? 0 : project.getBudgetMax();
        int budgetMin = project.getBudgetMin() == null ? 0 : project.getBudgetMin();
        return budgetMax == 0 ? budgetMin : budgetMax;
    }

    private ProjectListItemResponse toListItemResponse(Project project) {
        return new ProjectListItemResponse(
                project.getId(),
                project.getTitle(),
                project.getArea(),
                project.getEmploymentType().toApiValue(),
                project.getRecruitStatus().toApiValue(),
                project.getBudgetMin(),
                project.getBudgetMax(),
                project.getMonthlyWage(),
                project.getExpectedDurationDays(),
                project.getApplicationCount(),
                project.getDeadline(),
                buildDeadlineLabel(project.getDeadline()),
                List.copyOf(project.getCategories()),
                List.copyOf(project.getSkills()),
                project.getPostedAt(),
                project.getSummary());
    }

    private ProjectDetailResponse toDetailResponse(Project project) {
        return new ProjectDetailResponse(
                project.getId(),
                project.getTitle(),
                project.getArea(),
                project.getEmploymentType().toApiValue(),
                project.getRecruitStatus().toApiValue(),
                project.getBudgetMin(),
                project.getBudgetMax(),
                project.getMonthlyWage(),
                project.getExpectedDurationDays(),
                project.getApplicationCount(),
                project.getDeadline(),
                buildDeadlineLabel(project.getDeadline()),
                project.getKickoffSchedule(),
                List.copyOf(project.getCategories()),
                project.getProgressType(),
                project.getPlanningStatus(),
                project.getMeetingLocation(),
                project.getWorkDescription(),
                project.getWorkMethod(),
                List.copyOf(project.getSkills()),
                project.getPostedAt(),
                project.getSummary());
    }

    private String buildDeadlineLabel(LocalDate deadline) {
        long days = ChronoUnit.DAYS.between(LocalDate.now(), deadline);
        if (days < 0) {
            return "마감";
        }
        return "D-" + days;
    }
}
