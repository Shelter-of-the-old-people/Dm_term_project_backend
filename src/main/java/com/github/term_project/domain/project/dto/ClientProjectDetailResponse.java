package com.github.term_project.domain.project.dto;

import java.time.LocalDate;
import java.util.List;

public record ClientProjectDetailResponse(
        Long id,
        String title,
        LocalDate deadline,
        String deadlineLabel,
        String kickoffSchedule,
        String employmentType,
        List<String> categories,
        String progressType,
        String planningStatus,
        String meetingLocation,
        String workDescription,
        String workMethod,
        Integer applicationCount) {
}
