package com.github.term_project.domain.application.dto;

import java.time.LocalDate;

public record ClientApplicantSummaryResponse(
        Long applicationId,
        Long developerId,
        String developerName,
        String employmentType,
        Integer expectedAmount,
        LocalDate createdAt) {
}
