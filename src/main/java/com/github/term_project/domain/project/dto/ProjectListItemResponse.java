package com.github.term_project.domain.project.dto;

import java.time.LocalDate;
import java.util.List;

public record ProjectListItemResponse(
        Long id,
        String title,
        String area,
        String employmentType,
        String recruitStatus,
        Integer budgetMin,
        Integer budgetMax,
        Integer monthlyWage,
        Integer expectedDurationDays,
        Integer applicationCount,
        LocalDate deadline,
        String deadlineLabel,
        List<String> categories,
        List<String> skills,
        LocalDate createdAt,
        String summary) {
}
