package com.github.term_project.domain.project.dto;

import java.time.LocalDate;

public record ClientProjectSummaryResponse(
        Long id,
        String title,
        String employmentType,
        Integer budgetMin,
        Integer budgetMax,
        Integer monthlyWage,
        Integer applicationCount,
        LocalDate deadline,
        String deadlineLabel) {
}
