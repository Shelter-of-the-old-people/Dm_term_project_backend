package com.github.term_project.domain.application.dto;

import java.time.LocalDate;
import java.util.List;

public record DeveloperApplicationDetailResponse(
        Long applicationId,
        Long projectId,
        String projectTitle,
        String employmentType,
        Integer estimateAmount,
        Integer applicationCount,
        Integer expectedDurationDays,
        LocalDate deadline,
        String deadlineLabel,
        Integer workDays,
        Integer bidAmount,
        Integer headcount,
        String content,
        LocalDate createdAt,
        List<ApplicationOnsiteLineResponse> onsiteLines) {
}
