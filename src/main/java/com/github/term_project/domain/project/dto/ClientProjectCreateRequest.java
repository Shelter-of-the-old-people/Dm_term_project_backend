package com.github.term_project.domain.project.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

public record ClientProjectCreateRequest(
        @NotBlank(message = "title is required.") String title,
        @NotNull(message = "recruitmentDeadline is required.")
        @Future(message = "recruitmentDeadline must be in the future.")
        LocalDate recruitmentDeadline,
        @NotBlank(message = "projectType is required.") String projectType,
        @NotNull(message = "budgetAmount is required.")
        @Positive(message = "budgetAmount must be greater than 0.")
        Integer budgetAmount,
        @NotNull(message = "expectedDurationDays is required.")
        @Positive(message = "expectedDurationDays must be greater than 0.")
        Integer expectedDurationDays,
        @NotEmpty(message = "projectFields is required.") List<String> projectFields,
        @NotBlank(message = "planningStatus is required.") String planningStatus,
        @NotBlank(message = "meetingRegion is required.") String meetingRegion,
        @NotBlank(message = "workDescription is required.") String workDescription,
        @NotBlank(message = "progressMethod is required.") String progressMethod,
        @NotEmpty(message = "techStacks is required.") List<String> techStacks,
        String kickoffSchedule) {
}
