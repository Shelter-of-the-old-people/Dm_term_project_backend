package com.github.term_project.domain.application.service;

import com.github.term_project.domain.application.entity.Application;
import com.github.term_project.domain.application.entity.ApplicationOnsiteLine;
import com.github.term_project.domain.application.repository.ApplicationRepository;
import com.github.term_project.domain.project.entity.EmploymentType;
import com.github.term_project.domain.project.entity.Project;
import com.github.term_project.domain.project.repository.ProjectRepository;
import com.github.term_project.domain.user.entity.User;
import com.github.term_project.domain.user.repository.UserRepository;
import com.github.term_project.global.error.BusinessException;
import com.github.term_project.global.error.ErrorCode;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(4)
@RequiredArgsConstructor
public class ApplicationSeedDataInitializer implements ApplicationRunner {

    private final ApplicationRepository applicationRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (applicationRepository.count() > 0 || projectRepository.count() == 0) {
            return;
        }

        Map<String, User> usersByLoginId = userRepository.findAll().stream()
                .collect(Collectors.toMap(User::getLoginId, Function.identity()));

        Map<Integer, Project> projectsByDisplayOrder = projectRepository.findAll().stream()
                .collect(Collectors.toMap(Project::getDisplayOrder, Function.identity()));

        projectRepository.findAll().forEach(project -> project.syncApplicationCount(0));

        List<Application> seedApplications = List.of(
                buildOutsourcingApplication(
                        projectByDisplayOrder(projectsByDisplayOrder, 2, EmploymentType.OUTSOURCING),
                        userByLoginId(usersByLoginId, "developer1"),
                        24,
                        6100,
                        "I can deliver the dashboard and admin flow in a stable two-sprint plan."),
                buildOutsourcingApplication(
                        projectByDisplayOrder(projectsByDisplayOrder, 2, EmploymentType.OUTSOURCING),
                        userByLoginId(usersByLoginId, "developer2"),
                        28,
                        5900,
                        "I have recent React and Java delivery experience for service operations."),
                buildOutsourcingApplication(
                        projectByDisplayOrder(projectsByDisplayOrder, 2, EmploymentType.OUTSOURCING),
                        userByLoginId(usersByLoginId, "developer3"),
                        26,
                        6400,
                        "I can cover API design, batch work, and release stabilization together."),
                buildResidentApplication(
                        projectByDisplayOrder(projectsByDisplayOrder, 5, EmploymentType.RESIDENT),
                        userByLoginId(usersByLoginId, "developer1"),
                        "I can join the QA automation workflow and keep release checks predictable.",
                        List.of(buildResidentLine("QA automation", "mid", 1, 520, 1))),
                buildResidentApplication(
                        projectByDisplayOrder(projectsByDisplayOrder, 5, EmploymentType.RESIDENT),
                        userByLoginId(usersByLoginId, "developer2"),
                        "I can improve regression coverage and reporting for the release cycle.",
                        List.of(
                                buildResidentLine("QA automation", "mid", 1, 500, 1),
                                buildResidentLine("frontend support", "junior", 1, 420, 2))));

        seedApplications.forEach(application -> application.getProject().increaseApplicationCount());
        applicationRepository.saveAll(seedApplications);
    }

    private Application buildOutsourcingApplication(
            Project project,
            User developer,
            int workDays,
            int bidAmount,
            String content) {

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
            List<ApplicationOnsiteLine> onsiteLines) {

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

    private ApplicationOnsiteLine buildResidentLine(
            String skillCategory,
            String careerLevel,
            int headCount,
            int monthlyPay,
            int sortOrder) {

        return ApplicationOnsiteLine.builder()
                .skillCategory(skillCategory)
                .careerLevel(careerLevel)
                .headCount(headCount)
                .monthlyPay(monthlyPay)
                .sortOrder(sortOrder)
                .build();
    }

    private User userByLoginId(Map<String, User> usersByLoginId, String loginId) {
        User user = usersByLoginId.get(loginId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, loginId + " seed user is required.");
        }
        return user;
    }

    private Project projectByDisplayOrder(
            Map<Integer, Project> projectsByDisplayOrder,
            int displayOrder,
            EmploymentType employmentType) {

        Project project = projectsByDisplayOrder.get(displayOrder);
        if (project == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "Seed project displayOrder=" + displayOrder + " is required.");
        }
        if (project.getEmploymentType() != employmentType) {
            throw new BusinessException(
                    ErrorCode.APPLICATION_TYPE_MISMATCH,
                    "Seed project displayOrder=" + displayOrder + " employment type does not match.");
        }
        return project;
    }
}
