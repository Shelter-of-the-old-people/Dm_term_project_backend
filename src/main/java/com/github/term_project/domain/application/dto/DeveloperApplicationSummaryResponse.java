package com.github.term_project.domain.application.dto;

import java.time.LocalDate;

public record DeveloperApplicationSummaryResponse(
        Long applicationId,
        Long projectId,
        String projectTitle,
        String employmentType,
        Integer estimateAmount,
        Integer applicationCount,
        Integer workDays,
        Integer expectedDurationDays,
        LocalDate createdAt) {
}
