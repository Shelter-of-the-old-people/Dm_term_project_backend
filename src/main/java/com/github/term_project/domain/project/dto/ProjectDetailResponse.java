package com.github.term_project.domain.project.dto;

import java.time.LocalDate;
import java.util.List;

public record ProjectDetailResponse(
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
        String kickoffSchedule,
        List<String> categories,
        String progressType,
        String planningStatus,
        String meetingLocation,
        String workDescription,
        String workMethod,
        List<String> skills,
        LocalDate createdAt,
        String summary,
        String clientDisplayId,
        String clientRegion,
        Integer clientProjectCount,
        Integer clientContractCount,
        Integer clientTotalContractAmount) {
}
