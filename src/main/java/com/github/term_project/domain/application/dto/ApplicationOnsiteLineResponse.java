package com.github.term_project.domain.application.dto;

public record ApplicationOnsiteLineResponse(
        String position,
        String careerLevel,
        Integer headcount,
        Integer monthlyWage,
        Integer sortOrder) {
}
