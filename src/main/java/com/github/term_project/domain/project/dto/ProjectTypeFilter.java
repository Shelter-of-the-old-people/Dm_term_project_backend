package com.github.term_project.domain.project.dto;

import com.github.term_project.domain.project.entity.EmploymentType;
import com.github.term_project.global.error.BusinessException;
import com.github.term_project.global.error.ErrorCode;
import java.util.Arrays;
import java.util.Locale;

public enum ProjectTypeFilter {
    ALL(null),
    OUTSOURCING(EmploymentType.OUTSOURCING),
    RESIDENT(EmploymentType.RESIDENT);

    private final EmploymentType employmentType;

    ProjectTypeFilter(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public EmploymentType employmentType() {
        return employmentType;
    }

    public static ProjectTypeFilter from(String value) {
        if (value == null || value.isBlank()) {
            return ALL;
        }

        return Arrays.stream(values())
                .filter(candidate -> candidate.name().equals(value.trim().toUpperCase(Locale.ROOT)))
                .findFirst()
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.INVALID_INPUT,
                        "type must be one of all, outsourcing, resident."));
    }
}
