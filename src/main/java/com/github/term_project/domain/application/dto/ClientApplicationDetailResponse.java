package com.github.term_project.domain.application.dto;

import java.time.LocalDate;
import java.util.List;

public record ClientApplicationDetailResponse(
        Long applicationId,
        Long projectId,
        Long developerId,
        String developerName,
        String employmentType,
        Integer workDays,
        Integer bidAmount,
        Integer headcount,
        String content,
        LocalDate createdAt,
        List<ClientApplicationOnsiteLineResponse> onsiteLines) {
}
